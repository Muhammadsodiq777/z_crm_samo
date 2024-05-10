package com.zulfiqor.z_crm_zulfiqor.mapper.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ProductResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<ProductResponse> mapToResponseList(List<Product> product);

}
