package com.zulfiqor.z_crm_zulfiqor.model.dto.response.statistics;

import com.zulfiqor.z_crm_zulfiqor.model.dto.response.CategoryResponse;
import com.zulfiqor.z_crm_zulfiqor.model.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardTypeResponse {

    private String name;

    private CardType type;

    private CardDetailsResponse response;
}
