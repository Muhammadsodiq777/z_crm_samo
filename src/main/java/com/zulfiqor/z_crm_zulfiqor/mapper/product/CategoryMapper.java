package com.zulfiqor.z_crm_zulfiqor.mapper.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.request.CategoryRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.CategoryResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface CategoryMapper {
//    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);
    Category mapToEntity(CategoryRequest categoryRequest);

    @Mapping(target = "id", source = "id")
    CategoryResponse mapToDto(Category category);

    List<CategoryResponse> mapToResponseList(List<Category> categories);

//    @Mapping(target = "products", ignore = true)
//    CategoryResponse toDtoWithoutProducts(Category category);
}
