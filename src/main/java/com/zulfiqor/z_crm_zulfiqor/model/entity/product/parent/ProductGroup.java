package com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent;

import com.zulfiqor.z_crm_zulfiqor.model.base.BaseEntity;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ProductGroupResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PM_PRODUCT_GROUP")
public class ProductGroup extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "id")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "id")
    private ProductGroup parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<ProductGroup> children = new ArrayList<>();

    private Long updateBy;

    public ProductGroupResponse toResponse() {
        ProductGroupResponse response = new ProductGroupResponse();
        response.setId(getId());
        response.setTitle(getName());
        response.setImageUrl(getImageUrl());
//        response.setChildren(getChildren().stream().filter(BaseEntity::isActive).map(ProductGroup::toResponse).collect(Collectors.toList()));
        return response;
    }
}



