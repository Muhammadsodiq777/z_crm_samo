package com.zulfiqor.z_crm_zulfiqor.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApproveProductsResponse implements Serializable {

    @JsonProperty("typeName")
    private String typeName;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("products")
    List<ChildProductResponse> products;
}
