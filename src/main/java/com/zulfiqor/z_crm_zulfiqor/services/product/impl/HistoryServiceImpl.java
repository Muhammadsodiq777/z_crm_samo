package com.zulfiqor.z_crm_zulfiqor.services.product.impl;

import com.zulfiqor.z_crm_zulfiqor.exception.HttpResponseCode;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.filter.HistoryFilter;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ChildProductResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.history.PageResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.history.TransactionDetailsResponse;
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
import com.zulfiqor.z_crm_zulfiqor.services.product.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final InProductRepository inProductRepository;
    private final OutProductRepository outProductRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public BaseResponse<?> getHistory(TradePlaceStatus status, HistoryFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        Page<Transactions> all;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;

        try {
             if ((filter.getFromDate() != null && !filter.getFromDate().isEmpty()
                    && filter.getToDate() != null && !filter.getToDate().isEmpty())) {

                startDate = dateFormat.parse(filter.getFromDate());
                endDate = dateFormat.parse(filter.getToDate());
                all = transactionRepository
                        .findAllByStatusAndActiveTrueAndCreatedDateBetweenAndConfirmStatusNotOrderByCreatedDateDesc(status,
                                startDate, endDate, ConfirmStatus.IN_PROGRESS, pageable);
            }
            else if (filter.getFromDate() != null && !filter.getFromDate().isEmpty()) {

                startDate = dateFormat.parse(filter.getFromDate());
                all = transactionRepository
                        .findAllByStatusAndActiveTrueAndCreatedDateGreaterThanEqualAndConfirmStatusNotOrderByCreatedDateDesc(status,
                                startDate, ConfirmStatus.IN_PROGRESS, pageable);
            } else if (filter.getToDate() != null && !filter.getToDate().isEmpty()) {

                 endDate = dateFormat.parse(filter.getToDate());
                all = transactionRepository
                        .findAllByStatusAndActiveTrueAndCreatedDateLessThanEqualAndConfirmStatusNotOrderByCreatedDateDesc(status,
                                endDate, ConfirmStatus.IN_PROGRESS, pageable);
            }  else
                all = transactionRepository
                        .findAllByStatusAndActiveTrueAndConfirmStatusNotOrderByCreatedDateDesc(status,
                                ConfirmStatus.IN_PROGRESS, pageable);

        }
        catch (ParseException e) {
            return BaseResponse.error(HttpResponseCode.BAD_REQUEST.getCode(),
                    "Sana xato formatda berildi(Talab qilingan: yyyy-MM-dd");
        }
        List<Transactions> content = all.getContent();
        List<TransactionsResponse> responseList
                = ApproveProductsServiceImpl.transactionResponseList(content, formatter, userRepository);
        PageResponse response = new PageResponse(responseList);
        response.setPage(filter.getPage());
        response.setSize(all.getSize());
        response.setTotalPage(all.getTotalPages());
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<?> getHistoryDetails(TradePlaceStatus status, Long transactionId) {
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

        Transactions transactions = byId.get();
        Optional<User> createdUser = userRepository.findById(transactions.getCreatedBy());
        Optional<User> confirmedUser = userRepository.findById(transactions.getUpdatedBy());

        TransactionDetailsResponse detailsResponse =
                new TransactionDetailsResponse(
                        createdUser.isPresent() ? createdUser.get().getFio() : "",
                        formatter.format(transactions.getCreatedDate()),
                        confirmedUser.isPresent() ? confirmedUser.get().getFio() : "",
                        formatter.format(transactions.getUpdatedDate()),
                        transactions.getConfirmStatus(),
                        transactions.getComment(),
                        childProductResponses);
    return BaseResponse.success(detailsResponse);
    }
}
