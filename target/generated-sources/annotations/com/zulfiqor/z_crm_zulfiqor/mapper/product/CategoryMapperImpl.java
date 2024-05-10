package com.zulfiqor.z_crm_zulfiqor.mapper.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.request.CategoryRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.CategoryResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ProductGroupResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.Category;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.ProductGroup;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T18:38:43+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category mapToEntity(CategoryRequest categoryRequest) {
        if ( categoryRequest == null ) {
            return null;
        }

        Category category = new Category();

        category.setName( categoryRequest.name() );

        return category;
    }

    @Override
    public CategoryResponse mapToDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId( category.getId() );
        categoryResponse.setName( category.getName() );
        categoryResponse.setImageUrl( category.getImageUrl() );
        categoryResponse.setChildren( productGroupListToProductGroupResponseList( category.getChildren() ) );

        return categoryResponse;
    }

    @Override
    public List<CategoryResponse> mapToResponseList(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryResponse> list = new ArrayList<CategoryResponse>( categories.size() );
        for ( Category category : categories ) {
            list.add( mapToDto( category ) );
        }

        return list;
    }

    protected List<ProductGroupResponse> productGroupListToProductGroupResponseList(List<ProductGroup> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductGroupResponse> list1 = new ArrayList<ProductGroupResponse>( list.size() );
        for ( ProductGroup productGroup : list ) {
            list1.add( productGroupToProductGroupResponse( productGroup ) );
        }

        return list1;
    }

    protected ProductGroupResponse productGroupToProductGroupResponse(ProductGroup productGroup) {
        if ( productGroup == null ) {
            return null;
        }

        ProductGroupResponse productGroupResponse = new ProductGroupResponse();

        productGroupResponse.setId( productGroup.getId() );
        productGroupResponse.setImageUrl( productGroup.getImageUrl() );
        productGroupResponse.setChildren( productGroupListToProductGroupResponseList( productGroup.getChildren() ) );

        return productGroupResponse;
    }
}
