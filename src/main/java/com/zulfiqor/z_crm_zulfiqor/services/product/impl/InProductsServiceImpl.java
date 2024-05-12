package com.zulfiqor.z_crm_zulfiqor.services.product.impl;

import com.zulfiqor.z_crm_zulfiqor.exception.HttpResponseCode;
import com.zulfiqor.z_crm_zulfiqor.exception.NotFoundException;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.InProductsRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.SellProduct;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ChildProductResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ParentProductResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.PriceResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.SaveResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.InProducts;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Transactions;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.Product;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.ProductGroup;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import com.zulfiqor.z_crm_zulfiqor.repository.product.InProductRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.ProductGroupRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.ProductRepository;
import com.zulfiqor.z_crm_zulfiqor.services.notification.PushNotificationService;
import com.zulfiqor.z_crm_zulfiqor.services.product.InProductsService;
import com.zulfiqor.z_crm_zulfiqor.services.product.TransactionService;
import com.zulfiqor.z_crm_zulfiqor.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.zulfiqor.z_crm_zulfiqor.exception.HttpResponseCode.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class InProductsServiceImpl implements InProductsService {

    private final InProductRepository inProductRepository;
    private final ProductRepository productRepository;
    private final TransactionService transactionService;
    private final ProductGroupRepository productGroupRepository;
    private final PushNotificationService pushNotificationService;
    private final SecurityUtils securityUtils;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse<?> addProduct(InProductsRequest requests, String deviceId) {
        User user = securityUtils.getCurrentUser();
        SellProduct product = new SellProduct();
        product.setPlaceId(requests.getTradePlaceId());
        Transactions transaction = transactionService.createTransaction(requests.getComment(), TradePlaceStatus.BUY, product, deviceId, null);
        List<InProducts> productList = new ArrayList<>();
        for ( InProductsRequest.ProductRequest productsRequest : requests.getProducts()) {
            InProducts products = new InProducts();
            products.setProduct(getProductById(productsRequest.getProductId(), productsRequest.getQuantity()));
            products.setQuantity(productsRequest.getQuantity());
            products.setPrice(productsRequest.getPrice());
            products.setActive(true);
            products.setSoldQuantity(0L);
            products.setCreatedBy(user.getId());
            products.setTransactions(transaction);
            productList.add(products);
        }
        inProductRepository.saveAll(productList);
//        pushNotificationService.confirmProduct(transaction.getId());
//        logger.info("Mahsulotlar notificationga yuborildi:");
        return BaseResponse.success(new SaveResponse(transaction.getId()));
    }

    @Override
    public BaseResponse<?> updateProduct(String id, InProductsRequest productListRequest) {
//        TODO yangi qo'shilganda yoki update qilganda hamisha osha mahsulotni idsini qaytaring
        return BaseResponse.success("Mahsulotlar muvaffaqiyatli o'zgartirildi");
    }

    @Override
    public BaseResponse<?> getAllPresentProductsInWarehouse(Long id)  {
        List<ProductGroup> productGroups = productGroupRepository.findAllByParentId(id);
        if (productGroups.isEmpty())
            return BaseResponse.error(NOT_FOUND.getCode(), "Hech qanday guruhlar topilmadi");

        List<ParentProductResponse> parentProductResponses = new ArrayList<>();
        for (ProductGroup productGroup: productGroups) {
            List<Product> products = productRepository.findAllByActiveIsTrueAndProductGroup(productGroup.getId());
            List<ChildProductResponse> childProductResponses = getAllProducts(products);
            if (!childProductResponses.isEmpty()) {
                ParentProductResponse parentProductResponse = new ParentProductResponse();
                parentProductResponse.setName(productGroup.getName());
                parentProductResponse.setId(productGroup.getId());

                long totalNum = childProductResponses.stream().mapToLong(ChildProductResponse::getQuantity).sum();
                parentProductResponse.setTotalNumber(totalNum);
                parentProductResponse.setTotalPrice((childProductResponses
                        .stream().mapToDouble(ChildProductResponse::getPrice).sum()) * totalNum);

                parentProductResponse.setChildren(childProductResponses);
            parentProductResponses.add(parentProductResponse);
            }
        }

        return BaseResponse.success(parentProductResponses);
    }

    @Override
    public BaseResponse<?> searchProductByNameOrCode(String param) { /// bu ham vaqtinchalik yechim keyinroq optimization qilinadi
        List<Product> products = productRepository.findAllByNameContainsIgnoreCaseOrCodeContainsIgnoreCaseAndActiveIsTrue(param, param);

        Map<ProductGroup, ParentProductResponse> parentMap = products.stream()
                .map(Product::getProductGroup)
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        productGroup -> {
                            ParentProductResponse parentProductResponse = new ParentProductResponse();
                            parentProductResponse.setId(productGroup.getId());
                            parentProductResponse.setName(productGroup.getName());
                            return parentProductResponse;
                        }
                ));

        List<ParentProductResponse> responses = new ArrayList<>();
        parentMap.forEach((parent,resGroup)-> {
            List<Product> children = products.stream()
                    .filter(product -> product.getProductGroup().equals(parent))
                    .collect(Collectors.toList());


            List<ChildProductResponse> productResponse = getAllProducts(children);

            long totalNum = productResponse.stream().mapToLong(ChildProductResponse::getQuantity).sum();
            resGroup.setTotalNumber(totalNum);
            resGroup.setTotalPrice((productResponse
                    .stream().mapToDouble(ChildProductResponse::getPrice).sum()) * totalNum);

            resGroup.setChildren(productResponse);
            responses.add(resGroup);
        });
        return BaseResponse.success(!responses.isEmpty() ? responses : new ArrayList<>());
    }

    // if needed, return more fields
    @Override
    public BaseResponse<?> getInProductById(Long id) {
        Optional<InProducts> inProducts = inProductRepository.findById(id);
        if(inProducts.isEmpty())
            return BaseResponse.error(NOT_FOUND.getCode(), "Mahsulot topilmadi");
        ChildProductResponse response = new ChildProductResponse();
        InProducts products = inProducts.get();
        response.setId(products.getId());
        response.setQuantity(products.getQuantity() - products.getSoldQuantity());
        return BaseResponse.success(response);
    }

    private List<ChildProductResponse> getAllProducts(List<Product> products) {
        List<ChildProductResponse> responses = new ArrayList<>();

        for (Product product: products) {
            ChildProductResponse response = new ChildProductResponse();
            List<PriceResponse> priceResponses = getPriceList(product);
            if (!priceResponses.isEmpty()) {
                response.setId(product.getId());
                response.setName(product.getName());
                response.setCode(product.getCode());
                response.setDescription(product.getDescription());
                response.setPrice(priceResponses.stream().mapToDouble(PriceResponse::getPrice).sum());
                response.setQuantity(priceResponses.stream().mapToLong(PriceResponse::getQuantity).sum());
                response.setPrices(priceResponses);
                responses.add(response);
            }
        }
        return responses;
    }

    private List<PriceResponse> getPriceList(Product product) {
        List<PriceResponse> responses = new ArrayList<>();
        List<InProducts> listInProducts = inProductRepository.findAllByProduct(product);
        for(InProducts inProduct: listInProducts) {
            if (inProduct.getQuantity() - inProduct.getSoldQuantity() != 0) {
                PriceResponse response = new PriceResponse();
                response.setInproductId(inProduct.getId());
                response.setQuantity(inProduct.getQuantity() - inProduct.getSoldQuantity());
                response.setPrice(inProduct.getPrice());
                responses.add(response);
            }
        }
        return responses;
    }

    private Product getProductById(Long id, Long quantity) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty())
            throw new NotFoundException("Bazi mahsulotlar topilmadi! Iltimos qayta tekshirib ko'ring");
        Product product = byId.get();
        product.setTotalNum(product.getTotalNum() + quantity);
        return productRepository.save(product);
    }
}
