package com.zulfiqor.z_crm_zulfiqor.model.entity.product;

import com.zulfiqor.z_crm_zulfiqor.model.base.BaseEntity;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.TradePlaces;
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
@Table(name = "PM_OUT_PRODUCTS")
public class OutProducts extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "IN_PRODUCT_ID", referencedColumnName = "id")
    private InProducts inProducts;

    @Column(name = "QUANTITY")
    private Long quantity;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transactions transactions;

    private Long createdBy;

    private Long updateBy;
}
