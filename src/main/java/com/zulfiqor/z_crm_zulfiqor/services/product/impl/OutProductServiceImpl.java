package com.zulfiqor.z_crm_zulfiqor.services.product.impl;

import com.zulfiqor.z_crm_zulfiqor.exception.NotFoundException;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.ProductReq;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.SellProduct;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.SaveResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.InProducts;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.OutProducts;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Transactions;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.Product;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import com.zulfiqor.z_crm_zulfiqor.repository.product.InProductRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.OutProductRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.ProductRepository;
import com.zulfiqor.z_crm_zulfiqor.services.product.OutProductService;
import com.zulfiqor.z_crm_zulfiqor.services.product.TransactionService;
import com.zulfiqor.z_crm_zulfiqor.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zulfiqor.z_crm_zulfiqor.exception.HttpResponseCode.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OutProductServiceImpl implements OutProductService {

    private final OutProductRepository outProductRepository;
    private final InProductRepository inProductRepository;
    private final TransactionService transactionService;
    private final ProductRepository productRepository;
    private final SecurityUtils securityUtils;

    @Override
    public BaseResponse<?> sellProducts(User currentUser, SellProduct sellProduct, String deviceId) {
        Transactions transaction = transactionService.createTransaction("Ok",
                TradePlaceStatus.SELL, sellProduct, deviceId, sellProduct.getContractId());

        List<OutProducts> outProductsList = new ArrayList<>();
        for (ProductReq productReq : sellProduct.getProducts()) {
            Optional<Product> product = productRepository.findById(productReq.getProductId());
            if(product.isEmpty())
                return BaseResponse.error(NOT_FOUND.getCode(), "Mahsulot mavjud emas qayta tekshirib ko'ring");

            OutProducts outProducts = new OutProducts();
            outProducts.setUpdateBy(currentUser.getId());

            InProducts inProducts = sellProductPolicy(productReq.getInProductId(),
                    productReq.getQuantity(), product.get());
            outProducts.setInProducts(inProducts);
            outProducts.setQuantity(productReq.getQuantity());
            outProducts.setPrice(inProducts.getPrice());
            outProducts.setActive(true);
            outProducts.setTransactions(transaction);
            outProducts.setCreatedBy(securityUtils.getCurrentUser().getId());
            outProductsList.add(outProducts);
        }
        outProductRepository.saveAll(outProductsList);
        return BaseResponse.success(new SaveResponse(transaction.getId()));
    }

    private InProducts sellProductPolicy(Long id, Long quantity, Product product) {

        InProducts products = inProductRepository.findInProductsByIdAndActiveIsTrueAndProduct(id, product);
        if (products == null)
            throw new NotFoundException("Bazi mahsulotlar mavjud emas iltimos qayta tekshirib ko'ring");

        if((products.getSoldQuantity() + quantity) > products.getQuantity())
            throw new NotFoundException("Mahsulotlar soni yetarli emas mavjud: " +
                    (products.getQuantity()-products.getSoldQuantity()));

        if((products.getSoldQuantity() + quantity) == products.getQuantity())
            products.setActive(false);
        products.setSoldQuantity(products.getSoldQuantity() + quantity);
        products.setUpdateBy(securityUtils.getCurrentUser().getId());
        return inProductRepository.save(products);
    }
}
