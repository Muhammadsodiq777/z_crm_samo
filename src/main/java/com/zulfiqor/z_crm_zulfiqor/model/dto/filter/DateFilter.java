package com.zulfiqor.z_crm_zulfiqor.model.dto.filter;

import com.zulfiqor.z_crm_zulfiqor.model.dto.Filter;
import com.zulfiqor.z_crm_zulfiqor.model.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateFilter extends Filter {

    private Integer fromYear;
    private Integer fromMonth;
    private Integer fromDay;

    private Integer toYear;
    private Integer toMonth;
    private Integer toDay;

    private CardType cardType;
}
