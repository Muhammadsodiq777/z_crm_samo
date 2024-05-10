package com.zulfiqor.z_crm_zulfiqor.model.dto.response.statistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.ConfirmStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDetailsList {

    private Long id;
    private String fio;
    private String phone;
    private Long quantity;
    private Double perPrice;
    private Double totPrice;
    private String createdDate;
    private ConfirmStatus confirmStatus;
    private String checkPersonName;
    private String checkedDate;
    private String tradePlaceName;
}
