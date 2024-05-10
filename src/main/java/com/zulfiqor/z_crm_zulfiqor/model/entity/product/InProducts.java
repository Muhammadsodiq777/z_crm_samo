package com.zulfiqor.z_crm_zulfiqor.model.entity.product;

import com.zulfiqor.z_crm_zulfiqor.model.base.BaseEntity;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.Product;
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
@Table(name = "PM_IN_PRODUCTS")
public class InProducts extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private Product product;

    @Column(name = "QUANTITY")
    private Long quantity;

    @Column(name = "SOLD_QUANTITY")
    private Long soldQuantity;

    @Column(name = "PRICE")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transactions transactions;

    private Long createdBy;

    private Long updateBy;
}
