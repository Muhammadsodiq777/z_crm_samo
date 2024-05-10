package com.zulfiqor.z_crm_zulfiqor.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParentProductResponse implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name = "";
    @JsonProperty("imageUrl")
    private String imageUrl = "";
    @JsonProperty("totalPrice")
    private Double totalPrice;
    @JsonProperty("totalNumber")
    private Long totalNumber;
    @JsonProperty("children")
    private List<ChildProductResponse> children = new ArrayList<>();
}
