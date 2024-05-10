package com.zulfiqor.z_crm_zulfiqor.repository.product.custom.impl;

import com.zulfiqor.z_crm_zulfiqor.model.dto.filter.DateFilter;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.custom.CardDetailsCustomEntity;
import com.zulfiqor.z_crm_zulfiqor.repository.product.custom.StatisticsCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.query.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class StatisticsCustomRepositoryImpl implements StatisticsCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<CardDetailsCustomEntity> getPresentProductsListForCard(DateFilter filter) {
        String query = """
                SELECT pip.id,
                       ur.fio,
                       ur.phone_number AS phone,
                       pp.name AS product_name,
                       (pip.quantity - pip.sold_quantity) AS quantity,
                       pip.price AS per_price,
                       pip.price * (pip.quantity - pip.sold_quantity) AS tot_price,
                       TO_CHAR(pip.created_date, 'DD.MM.YYYY HH24:MI:SS') as created_date,
                       pt.confirm_status as confirm_status,
                       ur2.fio AS checked_person_name,
                       TO_CHAR(pt.updated_date, 'DD.MM.YYYY HH24:MI:SS') as checked_date,
                       ptp.name AS trade_place_name
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
                    AND (EXTRACT(DAY FROM pip.created_date) <= :toDay OR :toDay IS NULL)
                """;

        Query nativeQuery = entityManager.createNativeQuery(query, CardDetailsCustomEntity.class);
        setFilters(filter, nativeQuery);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        nativeQuery.setFirstResult((int) pageable.getOffset());
        nativeQuery.setMaxResults(pageable.getPageSize());

        List<CardDetailsCustomEntity> resultList = nativeQuery.getResultList();

        // Get total count for pagination
        Query countQuery = entityManager.createNativeQuery("SELECT COUNT(*) FROM (" + query + ") AS total");
        setFilters(filter, countQuery);
        long total = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(resultList, pageable, total);
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
