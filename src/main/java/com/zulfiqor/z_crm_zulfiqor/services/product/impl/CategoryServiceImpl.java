package com.zulfiqor.z_crm_zulfiqor.services.product.impl;

import com.zulfiqor.z_crm_zulfiqor.exception.NotFoundException;
import com.zulfiqor.z_crm_zulfiqor.mapper.product.CategoryMapper;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.CategoryRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.CategoryResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.SaveResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.Category;
import com.zulfiqor.z_crm_zulfiqor.repository.product.CategoryRepository;
import com.zulfiqor.z_crm_zulfiqor.services.product.CategoryService;
import com.zulfiqor.z_crm_zulfiqor.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper mapper;
    private final CategoryRepository categoryRepository;
    private final SecurityUtils securityUtils;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public BaseResponse<?> addCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        category.setUpdateBy(securityUtils.getCurrentUser().getId());
        category.setActive(true);
        categoryRepository.save(category);
        return BaseResponse.success(new SaveResponse(category.getId()));
    }

    @Override
    public BaseResponse<?> updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = getCategory(id, true);
        category.setName(categoryRequest.name());
        category.setUpdateBy(securityUtils.getCurrentUser().getId());
        categoryRepository.save(category);
        return BaseResponse.success(new SaveResponse(category.getId()));
    }

    @Override
    public BaseResponse<List<CategoryResponse>> searchByName(String name) {
        return Optional.of(categoryRepository.findAllByNameLikeIgnoreCase(name))
            .filter(list -> !list.isEmpty())
            .map(mapper::mapToResponseList)
            .map(categories -> new BaseResponse<>(true, 200, "success", categories))
            .orElse(new BaseResponse<>(false, -1, "Hech narsa topilmadi"));
    }

    @Override
    public BaseResponse<List<CategoryResponse>> getAllCategoriesAndChildType() {
        List<Category> categories = categoryRepository.findAllByActiveIsTrue();
        List<CategoryResponse> list = categories.stream().map(Category::toDto)
            .collect(Collectors.toList());

        return BaseResponse.success(!list.isEmpty() ? list : new ArrayList<>());
    }

    @Override
    public BaseResponse<?> deleteCategory(Long categoryId) {
        Category category = getCategory(categoryId, true);
        category.setActive(false);
        categoryRepository.save(category);
        return BaseResponse.success();
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
