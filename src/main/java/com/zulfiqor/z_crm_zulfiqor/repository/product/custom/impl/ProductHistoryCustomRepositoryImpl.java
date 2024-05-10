package com.zulfiqor.z_crm_zulfiqor.repository.product.custom.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ParentProductResponse;
import com.zulfiqor.z_crm_zulfiqor.repository.product.custom.ProductHistoryCustomRepository;
import jakarta.persistence.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductHistoryCustomRepositoryImpl implements ProductHistoryCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;


}
