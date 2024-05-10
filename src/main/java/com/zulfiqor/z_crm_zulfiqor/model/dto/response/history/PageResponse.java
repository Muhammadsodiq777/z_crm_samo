package com.zulfiqor.z_crm_zulfiqor.model.dto.response.history;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.statistics.CardDetailsTotal;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponse {
    private int page = 0;
    private int size = 0;
    private int totalPage = 0;
    private long totalElements = 0;
    private CardDetailsTotal totalSums;
    private List<?> data;

    public PageResponse(List<?> data) {
        this.data = data;
    }
}
