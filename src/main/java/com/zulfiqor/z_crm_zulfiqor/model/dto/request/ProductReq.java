package com.zulfiqor.z_crm_zulfiqor.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductReq {
    private Long productId;
    private Long inProductId;
    private Long quantity;
    private Double price;
}