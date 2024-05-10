package com.zulfiqor.z_crm_zulfiqor.repository.product;

import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.ProductGroup;
import com.zulfiqor.z_crm_zulfiqor.repository.product.custom.ProductSearchCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long>, ProductSearchCustomRepository {

    @Query(value = "select * from pm_product_group ppg where  ppg.id = ? and ppg.active = true and ppg.parent_id is not null", nativeQuery = true)
    Optional<ProductGroup> findChildById(Long id);

    ProductGroup findByIdAndCategoryId(Long id, Long categoryId);

    @Query(value = "select * from pm_product_group ppg where ppg.parent_id = ? and ppg.active = true;", nativeQuery = true)
    List<ProductGroup> findAllByParentId(Long parentId);

    List<ProductGroup> findByNameContainingIgnoreCaseAndParentIsNull(String name);

    Optional<ProductGroup> findByIdAndParentIsNull(Long typeId);

    Optional<ProductGroup> findByIdAndParentIsNotNull(Long id);

    List<ProductGroup> findByNameContainingIgnoreCaseAndParentIsNotNull(String name);
}
