package com.zulfiqor.z_crm_zulfiqor.services.product.impl;

import com.zulfiqor.z_crm_zulfiqor.exception.HttpResponseCode;
import com.zulfiqor.z_crm_zulfiqor.mapper.product.ProductMapper;
import com.zulfiqor.z_crm_zulfiqor.model.base.BaseEntity;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.ProductNamesRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ChildProductResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ParentProductResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.SaveResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.Product;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.ProductGroup;
import com.zulfiqor.z_crm_zulfiqor.repository.product.ProductGroupRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.ProductRepository;
import com.zulfiqor.z_crm_zulfiqor.services.product.ProductService;
import com.zulfiqor.z_crm_zulfiqor.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductGroupRepository productGroupRepository;
    @Override
    public BaseResponse<?> addProductNames(User currentUser, ProductNamesRequest request) {

        Optional<ProductGroup> byId = productGroupRepository.findChildById(request.productGroupId());
        if(byId.isEmpty())
            return BaseResponse.error(HttpResponseCode.NOT_FOUND.getCode(),
                    "Malumot topilmadi! Mahsulotni tekshirib, qayta urinib ko'ring");

        ProductGroup productGroup = byId.get();

        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setActive(true);
        product.setTotalNum(0L);
        product.setUpdateBy(currentUser.getId());
        product.setProductGroup(productGroup);
        productRepository.save(product);
        return BaseResponse.success(new SaveResponse(product.getId()));
    }

    @Override
    public BaseResponse<?> getByProductGroupId(Long id) {

        Optional<ProductGroup> optionalProductGroup = productGroupRepository.findById(id);
        if (optionalProductGroup.isEmpty())
            return BaseResponse.error(HttpResponseCode.NOT_FOUND.getCode(), HttpResponseCode.NOT_FOUND.getMessage());

        List<ProductGroup> productGroups = optionalProductGroup.get().getChildren();
        List<ParentProductResponse> responses = new ArrayList<>();
        productGroups = productGroups.stream().filter(BaseEntity::isActive).collect(Collectors.toList());
        for (ProductGroup productGroup: productGroups) {
            List<Product> products = productRepository.findAllByActiveIsTrueAndProductGroup(productGroup.getId());
            ParentProductResponse parentProductResponse = new ParentProductResponse();
            parentProductResponse.setName(productGroup.getName());
            parentProductResponse.setId(productGroup.getId());
            parentProductResponse.setChildren(getProductResponse(products));
            responses.add(parentProductResponse);
        }

        return BaseResponse.success(!responses.isEmpty() ? responses : new ArrayList<>());
    }

    private List<ChildProductResponse> getProductResponse(List<Product> products) {
        List<ChildProductResponse> responses = new ArrayList<>();
        products = products.stream().filter(BaseEntity::isActive).collect(Collectors.toList());

        for (Product product: products) {
            ChildProductResponse productResponse = new ChildProductResponse();
            productResponse.setName(product.getName());
            productResponse.setId(product.getId());
//            productResponse.setQuantity(product.getTotalNum());
            responses.add(productResponse);
        }

        return responses;
    }
}
