package com.zulfiqor.z_crm_zulfiqor.repository.product;

import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.Product;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select * from pm_products pp where  pp.product_group_id = ? and pp.active = true", nativeQuery = true)
    List<Product> findAllByActiveIsTrueAndProductGroup(Long productGroupId);
}
