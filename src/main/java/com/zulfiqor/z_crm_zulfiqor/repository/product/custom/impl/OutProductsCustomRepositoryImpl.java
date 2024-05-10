package com.zulfiqor.z_crm_zulfiqor.repository.product.custom.impl;

import com.zulfiqor.z_crm_zulfiqor.model.dto.HistoryFilter;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.OutProducts;
import com.zulfiqor.z_crm_zulfiqor.repository.product.custom.OutProductsCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class OutProductsCustomRepositoryImpl implements OutProductsCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OutProducts> getAllOutProducts(HistoryFilter historyFilter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OutProducts> productsCriteriaQuery = criteriaBuilder.createQuery(OutProducts.class);
        Root<OutProducts> productsRoot = productsCriteriaQuery.from(OutProducts.class);
        List<Predicate> predicateList = new ArrayList<>();

        if (historyFilter.getDate() != null && !historyFilter.getDate().isEmpty()) {
            predicateList.add(criteriaBuilder.equal(productsRoot.get("createdDate"), "date(" + historyFilter.getDate() + ")"));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        productsCriteriaQuery.where(predicates);
        return entityManager.createQuery(productsCriteriaQuery)
                .setFirstResult((historyFilter.getPage() - 1) * historyFilter.getSize())
                .setMaxResults(historyFilter.getSize())
                .getResultList();
    }

    @Override
    public int countOutProducts(HistoryFilter historyFilter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> userCriteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<OutProducts> userRoot = userCriteriaQuery.from(OutProducts.class);

        List<Predicate> predicateList = new ArrayList<>();
        if (historyFilter.getDate() != null && !historyFilter.getDate().isEmpty()) {
            predicateList.add(criteriaBuilder.equal(userRoot.get("createdDate"), "date(" + historyFilter.getDate() + ")"));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);

        userCriteriaQuery.select(criteriaBuilder.count(userRoot)).where(predicates);
        return entityManager.createQuery(userCriteriaQuery)
                .getFirstResult();
    }
}
