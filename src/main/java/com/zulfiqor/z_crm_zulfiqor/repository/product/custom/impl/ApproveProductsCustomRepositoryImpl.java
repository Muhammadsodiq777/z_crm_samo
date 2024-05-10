package com.zulfiqor.z_crm_zulfiqor.repository.product.custom.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ApproveProductsResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.history.TransactionsResponse;
import com.zulfiqor.z_crm_zulfiqor.repository.product.custom.ApproveProductsCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApproveProductsCustomRepositoryImpl implements ApproveProductsCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<ApproveProductsResponse> getUnconfirmedProductsDetails(Long transactionId) throws JsonProcessingException {
        String query = """
                SELECT
                    jsonb_build_object(
                            'typeName', ppg.name,
                            'number', sum(pip.quantity),
                            'products', jsonb_agg(
                                    jsonb_build_object(
                                            'name', pp.name,
                                            'price', pip.price,
                                            'quantity', pip.quantity
                                        )
                                )
                        )
                FROM pm_product_group ppg
                         JOIN pm_product_group ppg1 ON ppg.id = ppg1.parent_id
                    AND ppg.active = true
                         LEFT JOIN pm_products pp ON ppg1.id = pp.product_group_id
                    AND pp.active = true
                         LEFT JOIN pm_in_products pip ON pip.product_id = pp.id
                    AND pip.active = true
                         LEFT JOIN pm_transactions pt ON pt.id = pip.transaction_id
                    and pt.active = true
                WHERE pt.id = :transactionId
                  AND pt.confirm_status = 'IN_PROGRESS'
                GROUP BY ppg.name;
                        """;

        Object nativeQuery = entityManager.createNativeQuery(query)
                .setParameter("transactionId", transactionId)
                .getResultList();
        return mapper.readValue(mapper.writeValueAsString(nativeQuery), new TypeReference<>() {});
    }
}
