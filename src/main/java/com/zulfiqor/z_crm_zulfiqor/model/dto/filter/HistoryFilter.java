package com.zulfiqor.z_crm_zulfiqor.model.dto.filter;

import com.zulfiqor.z_crm_zulfiqor.model.dto.Filter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryFilter extends Filter {

    private String fromDate; /// 2023-12-12 | yyyy-mm-dd
    private String toDate;
}
