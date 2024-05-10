package com.zulfiqor.z_crm_zulfiqor.repository.product.custom.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ProductResponse;
import com.zulfiqor.z_crm_zulfiqor.repository.product.custom.ProductSearchCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchCustomRepositoryImpl implements ProductSearchCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductResponse> searchProductGroups(Integer isParent, @NotNull @NotEmpty String search) throws JsonProcessingException {

        String isParentQuery;

        if(isParent == 1)
            isParentQuery = "and pp.parent_id is null";
        else
            isParentQuery = "and pp.category_id is null";

        String query = "select jsonb_build_object(\n" +
                "                       'id', pp.id,\n" +
                "                       'name', pp.name,\n" +
                "                       'imageUrl', pp.image_url\n" +
                "                   ) from pm_product_group pp\n" +
                "         where pp.active = true\n" + isParentQuery+
                "           and pp.name ilike '%"+search+"%'";


        Object nativeQuery = entityManager.createNativeQuery(query)
                .getResultList();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(mapper.writeValueAsString(nativeQuery), new TypeReference<>() {});
    }
}
