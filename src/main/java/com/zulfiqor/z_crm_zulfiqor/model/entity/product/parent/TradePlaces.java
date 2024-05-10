package com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent;

import com.zulfiqor.z_crm_zulfiqor.model.base.BaseEntity;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.AddressResponse;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PM_TRADE_PLACES")
public class TradePlaces extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    private TradePlaceStatus tradeStatus;

    private Long updateBy;

    public AddressResponse toResponse() {
        AddressResponse response = new AddressResponse();
        response.setId(getId());
        response.setName(getName());
        response.setDescription(getDescription());
        return response;
    }
}
