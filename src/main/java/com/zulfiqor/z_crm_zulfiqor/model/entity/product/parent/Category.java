package com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent;

import com.zulfiqor.z_crm_zulfiqor.model.base.BaseEntity;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.CategoryResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PM_CATEGORY")
public class Category extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    private String imageUrl;

    @OneToMany(mappedBy =  "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductGroup> children = new ArrayList<>();

    private Long updateBy;

    public CategoryResponse toDto() {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(getId());
        categoryResponse.setImageUrl(getImageUrl());
        categoryResponse.setName(getName());

        categoryResponse.setChildren(getChildren().stream()
                .filter(BaseEntity::isActive)
                .map(ProductGroup::toResponse)
                .collect(Collectors.toList()));

        return categoryResponse;
    }

}
