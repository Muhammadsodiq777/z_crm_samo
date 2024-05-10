package com.zulfiqor.z_crm_zulfiqor.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InProductsRequest{
    private String comment;
    private Long tradePlaceId;
    private List<ProductRequest> products;

    @Getter
    @Setter
 public static class ProductRequest {
        private Long productId;
        private Long quantity;
        private Double price;
    }
}


