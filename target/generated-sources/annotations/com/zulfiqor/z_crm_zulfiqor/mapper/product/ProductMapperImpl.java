package com.zulfiqor.z_crm_zulfiqor.mapper.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ProductResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.Product;
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
public class ProductMapperImpl implements ProductMapper {

    @Override
    public List<ProductResponse> mapToResponseList(List<Product> product) {
        if ( product == null ) {
            return null;
        }

        List<ProductResponse> list = new ArrayList<ProductResponse>( product.size() );
        for ( Product product1 : product ) {
            list.add( productToProductResponse( product1 ) );
        }

        return list;
    }

    protected ProductResponse productToProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse productResponse = new ProductResponse();

        productResponse.setId( product.getId() );
        productResponse.setName( product.getName() );
        productResponse.setImageUrl( product.getImageUrl() );

        return productResponse;
    }
}
