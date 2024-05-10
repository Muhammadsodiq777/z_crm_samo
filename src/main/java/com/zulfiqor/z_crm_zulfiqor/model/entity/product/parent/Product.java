package com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent;

import com.zulfiqor.z_crm_zulfiqor.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PM_PRODUCTS")
public class Product extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "TOTAL_NUM")
    private Long totalNum;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_GROUP_ID", referencedColumnName = "ID")
    private ProductGroup productGroup;

    private Long updateBy;

}
