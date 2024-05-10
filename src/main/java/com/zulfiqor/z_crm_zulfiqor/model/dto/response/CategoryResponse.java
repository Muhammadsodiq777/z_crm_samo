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
public class CategoryResponse {
        Long id;
        String name;
        String imageUrl;
        List<ProductGroupResponse> children = new ArrayList<>();
}
