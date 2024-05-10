package com.zulfiqor.z_crm_zulfiqor.services.product.impl;

import com.zulfiqor.z_crm_zulfiqor.exception.HttpResponseCode;
import com.zulfiqor.z_crm_zulfiqor.exception.NotFoundException;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.ProductGroupRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ProductGroupResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.SaveResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.Category;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.ProductGroup;
import com.zulfiqor.z_crm_zulfiqor.repository.product.CategoryRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.ProductGroupRepository;
import com.zulfiqor.z_crm_zulfiqor.services.product.ProductGroupService;
import com.zulfiqor.z_crm_zulfiqor.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductGroupServiceImpl implements ProductGroupService {

    private final ProductGroupRepository productGroupRepository;
    private final CategoryRepository categoryRepository;
    private final SecurityUtils securityUtils;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public BaseResponse<?> addParentProductType(Long categoryId, ProductGroupRequest request) {
        Category category = getCategory(categoryId, true);
        ProductGroup productGroup = new ProductGroup();
        productGroup.setName(request.name());
        productGroup.setCategory(category);
        productGroup.setActive(true);
        productGroupRepository.save(productGroup);
        return BaseResponse.success(new SaveResponse(productGroup.getId()));
    }

    @Override
    public BaseResponse<?> updateParentProductType(Long typeId, ProductGroupRequest request) {
        Optional<ProductGroup> byId = productGroupRepository.findById(typeId);
        if(byId.isEmpty())
            return BaseResponse.error(HttpResponseCode.NOT_FOUND.getCode(), "Guruh topilmadi qayta tekshirib ko'ring");
        ProductGroup productGroup = byId.get();
        productGroup.setName(request.name());
        productGroup.setUpdateBy(securityUtils.getCurrentUser().getId());
        productGroupRepository.save(productGroup);
        return BaseResponse.success(productGroup);
    }

    @Override
    public BaseResponse<?> searchParentProductType(String name) {
        List<ProductGroup> groups = productGroupRepository.findByNameContainingIgnoreCaseAndParentIsNull(name);
        return mapSearchResponse(groups);
    }


    @Override
    public BaseResponse<?> deleteParentProductType(Long typeId) {
        Optional<ProductGroup> byId = productGroupRepository.findByIdAndParentIsNull(typeId);
        if (byId.isEmpty())
            return BaseResponse.error(HttpResponseCode.NOT_FOUND.getCode(), "Hech narsa topilmadi");
        ProductGroup productGroup = byId.get();
        productGroup.setActive(false);
        productGroupRepository.save(productGroup);
        return BaseResponse.success("Mahsulot o'chirildi");
    }

    @Override
    public BaseResponse<?> addChildProductGroup(Long categoryId, ProductGroupRequest request) {

        ProductGroup byId = productGroupRepository.findByIdAndCategoryId(request.id(), categoryId);
        if (byId == null) {
            logger.info(String.format("Ushbu Kategoriyaga bunday id: %s bola mavjud emas", request.id()));
            throw new NotFoundException(String.format("Ushbu Kategoriyaga bunday id: %s bola mavjud emas", request.id()));
        }

        ProductGroup group = new ProductGroup();
        group.setName(request.name());
        group.setParent(byId);
        group.setUpdateBy(securityUtils.getCurrentUser().getId());
        group.setActive(true);
        productGroupRepository.save(group);
        return BaseResponse.success("Child success add!");
    }

    @Override
    public BaseResponse<?> updateChildProductGroup(Long groupId, ProductGroupRequest childRequest) {
        Optional<ProductGroup> byIdAndParentIsNull = productGroupRepository.findByIdAndParentIsNotNull(groupId);
        if (byIdAndParentIsNull.isEmpty())
            return BaseResponse.error(HttpResponseCode.NOT_FOUND.getCode(), "Hech narsa topilmadi");

        ProductGroup productGroup = byIdAndParentIsNull.get();
        productGroup.setUpdateBy(securityUtils.getCurrentUser().getId());
        productGroup.setName(childRequest.name());
        productGroupRepository.save(productGroup);
        return BaseResponse.success("Updated");
    }

    @Override
    public BaseResponse<?> searchFromProductGroup(String name) {
        List<ProductGroup> groups = productGroupRepository.findByNameContainingIgnoreCaseAndParentIsNotNull(name);
        return mapSearchResponse(groups);
    }

    private BaseResponse<?> mapSearchResponse(List<ProductGroup> groups) {
        if (groups.isEmpty())
            return BaseResponse.error(HttpResponseCode.NOT_FOUND.getCode(), "Hech narsa topilmadi");

        List<ProductGroupResponse> groupResponseList = new ArrayList<>();
        for (ProductGroup group : groups) {
            ProductGroupResponse response = new ProductGroupResponse();
            response.setId(group.getId());
            response.setTitle(group.getName());
            response.setChildren(null);
            groupResponseList.add(response);
        }
        return BaseResponse.success(groupResponseList);
    }

    @Override
    public BaseResponse<?> deleteChildProductGroup(Long groupId) {
        Optional<ProductGroup> byIdAndParentIsNull = productGroupRepository.findByIdAndParentIsNotNull(groupId);
        if (byIdAndParentIsNull.isEmpty())
            return BaseResponse.error(HttpResponseCode.NOT_FOUND.getCode(), "Hech narsa topilmadi");

        ProductGroup productGroup = byIdAndParentIsNull.get();
        productGroup.setUpdateBy(securityUtils.getCurrentUser().getId());
        productGroup.setActive(false);
        productGroupRepository.save(productGroup);
        return BaseResponse.success("Deleted");
    }

    @Override
    public BaseResponse<?> getProductGroup(Long parentId) {
        // TODO: 12/15/2023 add parent id
        List<ProductGroup> allByParentIsNotNull = productGroupRepository.findAllByParentId(parentId);
        if(allByParentIsNotNull.isEmpty())
            return BaseResponse.success(new ArrayList<>());
        List<ProductGroupResponse> groupResponseList = new ArrayList<>();
        for (ProductGroup group : allByParentIsNotNull) {
            ProductGroupResponse response = new ProductGroupResponse();
            response.setId(group.getId());
            response.setTitle(group.getName());
            groupResponseList.add(response);
        }
        return BaseResponse.success(groupResponseList);
    }


    private Category getCategory(Long id, boolean active) {
        Optional<Category> category =  categoryRepository.findByIdAndActive(id, active);
        if (category.isEmpty()) {
            logger.info(String.format("Ushbu %s bilan hech qanday category topilmadi", id));
            throw new NotFoundException(String.format("Ushbu %s bilan hech qanday category topilmadi", id));
        }
        return category.get();
    }

}
