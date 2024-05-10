package com.zulfiqor.z_crm_zulfiqor.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductGroupResponse {
    private Long id;
    private String title;
    private String imageUrl;
    private List<ProductGroupResponse> children = new ArrayList<>();
}
