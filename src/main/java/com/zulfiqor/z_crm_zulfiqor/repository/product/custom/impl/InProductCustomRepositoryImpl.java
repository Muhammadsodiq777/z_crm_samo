package com.zulfiqor.z_crm_zulfiqor.repository.product.custom.impl;

import com.zulfiqor.z_crm_zulfiqor.model.dto.HistoryFilter;
import com.zulfiqor.z_crm_zulfiqor.model.dto.filter.DateFilter;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.InProducts;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Transactions;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.custom.CardDetailsCustomEntity;
import com.zulfiqor.z_crm_zulfiqor.repository.product.custom.InProductCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.hibernate.query.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InProductCustomRepositoryImpl implements InProductCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<InProducts> getAllInProducts(HistoryFilter historyFilter) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<InProducts> productsCriteriaQuery = criteriaBuilder.createQuery(InProducts.class);
        Root<InProducts> productsRoot = productsCriteriaQuery.from(InProducts.class);
        List<Predicate> predicateList = new ArrayList<>();
        if (historyFilter.getDate() != null) {
            predicateList.add(criteriaBuilder.equal(productsRoot.get("createdDate"), "date(" + historyFilter.getDate() + ")"));
        }
        if (historyFilter.getStatus() != null) {
            Join<InProducts, Transactions> transactionsJoin = productsRoot.join("transactions", JoinType.LEFT);
            predicateList.add(criteriaBuilder.equal(transactionsJoin.get("confirmStatus"), historyFilter.getStatus()));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        productsCriteriaQuery.where(predicates);
        return entityManager.createQuery(productsCriteriaQuery)
                .setMaxResults(historyFilter.getSize())
                .setFirstResult((historyFilter.getPage() - 1) * historyFilter.getSize())
                .getResultList();
    }

    @Override
    public int countInProducts(HistoryFilter historyFilter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<InProducts> userCriteriaQuery = criteriaBuilder.createQuery(InProducts.class);
        Root<InProducts> userRoot = userCriteriaQuery.from(InProducts.class);
        List<Predicate> predicateList = new ArrayList<>();
        if (historyFilter.getDate() != null && !historyFilter.getDate().isEmpty()) {
            predicateList.add(criteriaBuilder.equal(userRoot.get("createdDate"), "date(" + historyFilter.getDate() + ")"));
        }
        if (historyFilter.getStatus() != null) {
            Join<InProducts, Transactions> transactionsJoin = userRoot.join("transactions", JoinType.LEFT);
            predicateList.add(criteriaBuilder.equal(transactionsJoin.get("confirmStatus"), historyFilter.getStatus()));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        userCriteriaQuery.where(predicates);
        return entityManager.createQuery(userCriteriaQuery).getResultList().size();
    }

    @Override
    public List<CardDetailsCustomEntity> getPresentProductsListForCard(DateFilter filter) {

        String query = """
                SELECT pip.id,
                       ur.fio,
                       ur.phone_number AS phone,
                       pp.name AS productName,
                       (pip.quantity - pip.sold_quantity) AS quantity,
                       pip.price AS perPrice,
                       pip.price * (pip.quantity - pip.sold_quantity) AS totPrice,
                       TO_CHAR(pip.created_date, 'DD.MM.YYYY HH24:MI:SS'),
                       pt.confirm_status,
                       ur2.fio AS checkPersonName,
                       TO_CHAR(pt.updated_date, 'DD.MM.YYYY HH24:MI:SS') AS checkedDate,
                       ptp.name AS tradePlaceName
                FROM pm_in_products pip
                         INNER JOIN pm_products pp ON pp.id = pip.product_id
                    AND pip.active = true
                         LEFT JOIN users ur ON pip.created_by = ur.id
                         LEFT JOIN pm_transactions pt ON pt.id = pip.transaction_id
                         LEFT JOIN users ur2 ON ur2.id = pt.updated_by
                         LEFT JOIN pm_trade_places ptp ON ptp.id = pt.trade_places_id
                WHERE
                    (EXTRACT(YEAR FROM pip.created_date) >= :fromYear OR :fromYear IS NULL)
                    AND (EXTRACT(MONTH FROM pip.created_date) >= :fromMonth OR :fromMonth IS NULL)
                    AND (EXTRACT(DAY FROM pip.created_date) >= :fromDay OR :fromDay IS NULL)
                    AND (EXTRACT(YEAR FROM pip.created_date) <= :toYear OR :toYear IS NULL)
                    AND (EXTRACT(MONTH FROM pip.created_date) <= :toMonth OR :toMonth IS NULL)
                    AND (EXTRACT(DAY FROM pip.created_date) <= :toDay OR :toDay IS NULL)\s
                """;
        Query nativeQuery = entityManager.createNativeQuery(query, CardDetailsCustomEntity.class);
        setFilters(filter, nativeQuery);

        return null;
    }

    private void setFilters(DateFilter filterDto, Query nativeQuery) {
        nativeQuery.setParameter("fromYear", new TypedParameterValue<>(StandardBasicTypes.INTEGER, filterDto.getFromYear()));
        nativeQuery.setParameter("fromMonth", new TypedParameterValue<>(StandardBasicTypes.INTEGER, filterDto.getFromMonth()));
        nativeQuery.setParameter("fromDay", new TypedParameterValue<>(StandardBasicTypes.INTEGER, filterDto.getFromDay()));

        nativeQuery.setParameter("toYear", new TypedParameterValue<>(StandardBasicTypes.INTEGER, filterDto.getToYear()));
        nativeQuery.setParameter("toMonth", new TypedParameterValue<>(StandardBasicTypes.INTEGER, filterDto.getToMonth()));
        nativeQuery.setParameter("toDay", new TypedParameterValue<>(StandardBasicTypes.INTEGER, filterDto.getToDay()));
    }
}
