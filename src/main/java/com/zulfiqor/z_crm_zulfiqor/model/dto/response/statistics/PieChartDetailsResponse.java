package com.zulfiqor.z_crm_zulfiqor.model.dto.response.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PieChartDetailsResponse {

    private Integer order;

    private String name;

    private Double amount;
}
