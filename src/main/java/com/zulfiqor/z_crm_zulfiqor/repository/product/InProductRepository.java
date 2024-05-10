package com.zulfiqor.z_crm_zulfiqor.repository.product;

import com.zulfiqor.z_crm_zulfiqor.model.entity.product.InProducts;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Transactions;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.Product;
import com.zulfiqor.z_crm_zulfiqor.repository.product.custom.InProductCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface InProductRepository extends JpaRepository<InProducts, Long>, InProductCustomRepository {

    List<InProducts> findAllByProduct(Product product);

    InProducts findInProductsByIdAndActiveIsTrueAndProduct(Long id, Product product);

    List<InProducts> findAllByTransactionsAndActiveTrue(Transactions transactions);

    List<InProducts> findAllByActiveIsTrueAndCreatedDateBetween(Date startDate, Date endDate);

}
