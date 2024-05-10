package com.zulfiqor.z_crm_zulfiqor.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellProduct {
    private Long placeId;
    private Long contractId;
    private List<ProductReq> products;
}
