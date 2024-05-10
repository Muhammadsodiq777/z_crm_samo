package com.zulfiqor.z_crm_zulfiqor.services.product.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zulfiqor.z_crm_zulfiqor.exception.HttpResponseCode;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ApproveProductsResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ChildProductResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.history.TransactionsResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.InProducts;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.OutProducts;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Transactions;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.ConfirmStatus;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import com.zulfiqor.z_crm_zulfiqor.repository.UserRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.InProductRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.OutProductRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.TransactionRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.custom.ApproveProductsCustomRepository;
import com.zulfiqor.z_crm_zulfiqor.services.product.ApproveProductsService;
import com.zulfiqor.z_crm_zulfiqor.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus.BUY;
import static com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus.SELL;

@Service
@RequiredArgsConstructor
public class ApproveProductsServiceImpl implements ApproveProductsService {

    private final InProductRepository inProductRepository;
    private final OutProductRepository outProductRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final TransactionRepository transactionRepository;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public BaseResponse<?> getAllUnApprovedProducts() {
        List<Transactions> content = transactionRepository.findAllByConfirmStatusAndActiveTrue(ConfirmStatus.IN_PROGRESS);
        List<TransactionsResponse> responseList = transactionResponseList(content, formatter, userRepository);

        return BaseResponse.success(responseList);
    }

    static List<TransactionsResponse> transactionResponseList(List<Transactions> content, SimpleDateFormat formatter, UserRepository userRepository) {
        List<TransactionsResponse> responseList = new ArrayList<>();
        for (Transactions tr : content) {
            TransactionsResponse response = new TransactionsResponse();
            response.setTransactionId(tr.getId());
            response.setStatus(tr.getConfirmStatus());
            response.setCreatedDate(formatter.format(tr.getCreatedDate()));
            response.setTradeStatus(tr.getStatus());
            response.setTradePlace(tr.getTradePlaces().getName());

            if (tr.getCreatedBy() != null) {
                Optional<User> byId = userRepository.findById(tr.getCreatedBy());
                response.setFio(byId.isEmpty() ? "" : byId.get().getFio());
            }
            responseList.add(response);
        }
        return responseList;
    }

    // hozircha history bilan bir xil keyingi releaselarga o'zgartiramiz va hozircha duplicate turibdi
    @Override
    public BaseResponse<?> getAllUnApprovedProductDetails(TradePlaceStatus status, Long transactionId) {
        Optional<Transactions> byId = transactionRepository.findById(transactionId);
        if(byId.isEmpty())
            return BaseResponse.error(HttpResponseCode.NOT_FOUND.getCode(), HttpResponseCode.NOT_FOUND.getMessage());

        List<ChildProductResponse> childProductResponses = new ArrayList<>();
        switch (status){
            case BUY -> {
                List<InProducts> inProducts = inProductRepository.findAllByTransactionsAndActiveTrue(byId.get());
                if(inProducts.isEmpty())
                    return BaseResponse.error(HttpResponseCode.NOT_FOUND.getCode(), HttpResponseCode.NOT_FOUND.getMessage());
                for (InProducts product : inProducts) {
                    ChildProductResponse response = new ChildProductResponse();
                    response.setId(product.getId());
                    response.setName(product.getProduct().getName());
                    response.setPrice(product.getPrice());
                    response.setQuantity(product.getQuantity());
                    response.setDate(formatter.format(product.getCreatedDate()));
                    childProductResponses.add(response);
                }
            }
            case SELL ->{
                List<OutProducts> outProducts = outProductRepository.findAllByTransactionsAndActiveTrue(byId.get());
                if(outProducts.isEmpty())
                    return BaseResponse.error(HttpResponseCode.NOT_FOUND.getCode(), HttpResponseCode.NOT_FOUND.getMessage());
                for (OutProducts product : outProducts) {
                    ChildProductResponse response = new ChildProductResponse();
                    response.setId(product.getId());
                    response.setName(product.getInProducts().getProduct().getName());
                    response.setPrice(product.getPrice());
                    response.setQuantity(product.getQuantity());
                    response.setDate(formatter.format(product.getCreatedDate()));
                    childProductResponses.add(response);
                }
            }
        }
        return BaseResponse.success(childProductResponses);
    }

    @Override
    public BaseResponse<?> approveProductsByTransactionId(ConfirmStatus status, Long transactionId) {
        return transactionRepository.findById(transactionId)
                .map(transactions -> {
                    transactions.setUpdatedBy(securityUtils.getCurrentUser().getId());
                    transactions.setConfirmStatus(status);
                    transactionRepository.save(transactions);
                    return BaseResponse.success();
                })
                .orElseGet(() -> BaseResponse.error(404, "Bunday transaksiya topilmadi, Iltimos qayta urinib ko'ring"));
    }
}
