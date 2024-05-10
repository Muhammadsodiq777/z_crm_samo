package com.zulfiqor.z_crm_zulfiqor.model.dto.response.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDetailedResponse {

    private Long totalProducts;

    private BigDecimal totalPrice;

    private List<CardDetailsResponse> responses;
}
