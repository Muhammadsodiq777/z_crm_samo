package com.zulfiqor.z_crm_zulfiqor.model.entity.product.custom;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.ConfirmStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CardDetailsCustomEntity {

    @Id
    private Long id;
    private String fio;
    private String phone;

    @Column(name = "product_name")
    private String productName;

    private Long quantity;

    @Column(name = "per_price")
    private Double perPrice;

    @Column(name = "tot_price")
    private Double totPrice;

    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "confirm_status")
    private String confirmStatus;

    @Column(name = "checked_person_name")
    private String checkedPersonName;

    @Column(name = "checked_date")
    private String checkedDate;

    @Column(name = "trade_place_name")
    private String tradePlaceName;

}
