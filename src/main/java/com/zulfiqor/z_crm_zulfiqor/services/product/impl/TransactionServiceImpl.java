package com.zulfiqor.z_crm_zulfiqor.services.product.impl;

import com.zulfiqor.z_crm_zulfiqor.exception.HttpResponseCode;
import com.zulfiqor.z_crm_zulfiqor.exception.NotFoundException;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.SellProduct;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ChildProductResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.history.TransactionDetailsResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Contract;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.InProducts;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.OutProducts;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Transactions;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.Product;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.TradePlaces;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.ConfirmStatus;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import com.zulfiqor.z_crm_zulfiqor.repository.product.*;
import com.zulfiqor.z_crm_zulfiqor.services.product.TransactionService;
import com.zulfiqor.z_crm_zulfiqor.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus.BUY;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TradePlacesRepository tradePlacesRepository;
    private final InProductRepository inProductRepository;
    private final OutProductRepository outProductRepository;
    private final SecurityUtils securityUtils;
    private final ContractRepository contractRepository;

    @Override
    public Transactions createTransaction(String comment, TradePlaceStatus status, SellProduct product, String deviceId, Long contractId) {
        Transactions transactions = new Transactions();
        transactions.setStatus(status);
        transactions.setComment(comment);
        transactions.setConfirmStatus(ConfirmStatus.IN_PROGRESS);
        transactions.setActive(true);
        transactions.setTradePlaces(product.getPlaceId() != null ? getInTradePlace(product.getPlaceId(), status) : null);
        transactions.setContract(contractId != null ? getContract(contractId): null);
        transactions.setCreatedBy(securityUtils.getCurrentUser().getId());
        transactionRepository.save(transactions);
        return transactions;
    }

    @Override
    public Transactions updateTransaction(Transactions transactions, ConfirmStatus status) {
        transactions.setConfirmStatus(status);
        return transactionRepository.save(transactions);
    }


    @Override
    public BaseResponse<?> getTransactionById(Long transactionId) {
        Optional<Transactions> optionalTransactions = transactionRepository.findById(transactionId);
        if (optionalTransactions.isEmpty())
            return BaseResponse.error(HttpResponseCode.NOT_FOUND.getCode(), HttpResponseCode.NOT_FOUND.getMessage());
        Transactions transactions = optionalTransactions.get();
        TransactionDetailsResponse response = new TransactionDetailsResponse();
//        response.setId(transactions.getId());
//        response.setComment(transactions.getComment());
//        response.setStatus(transactions.getConfirmStatus());
//        response.setPlaceStatus(transactions.getStatus());
//        response.setCreatedDate(transactions.getCreatedDate());
//        response.setCheckedDate(transactions.getUpdatedDate());
        Contract contract = transactions.getContract();
//        response.setContractNumber(contract != null ? contract.getContractNumber() : null);
        List<ChildProductResponse> responses = new ArrayList<>();
        switch (transactions.getStatus()) {
            case BUY -> {
                List<InProducts> products = inProductRepository.findAllByTransactionsAndActiveTrue(transactions);
                for (InProducts product: products) {
                    ChildProductResponse productResponse = new ChildProductResponse();
                    productResponse.setId(product.getId());
                    Product nameProduct = product.getProduct();
                    productResponse.setName(nameProduct.getName());
                    productResponse.setDate(product.getCreatedDate().toString());
                    productResponse.setQuantity(product.getQuantity());
                    productResponse.setPrice(product.getPrice());
                    productResponse.setImageUrl(nameProduct.getImageUrl());
                    responses.add(productResponse);
                }
            }
            case SELL -> {
                List<OutProducts> products = outProductRepository.findAllByTransactions(transactions);
                for (OutProducts product: products) {
                    ChildProductResponse productResponse = new ChildProductResponse();
                    productResponse.setId(product.getId());
                    Product nameProduct = product.getInProducts().getProduct();
                    productResponse.setName(nameProduct.getName());
                    productResponse.setDate(product.getCreatedDate().toString());
                    productResponse.setQuantity(product.getQuantity());
                    productResponse.setPrice(product.getPrice());
                    productResponse.setImageUrl(nameProduct.getImageUrl());
                    responses.add(productResponse);
                }
            }
        }

        response.setProducts(responses);
        return BaseResponse.success(response);
    }

    private TradePlaces getInTradePlace(Long id, TradePlaceStatus status) {
        TradePlaces byTradeStatus = tradePlacesRepository.findByIdAndTradeStatus(id, status);
        if (byTradeStatus == null)
            throw new NotFoundException("Iltimos, mahsulotni " + (status == BUY ? "qayerdan sotib olinganini kiriting": "qayerga sotilayotganini kiriting"));
        return byTradeStatus;
    }

    private Contract getContract(Long id) {
        Optional<Contract> contract = contractRepository.findById(id);
        return contract.orElseGet(Contract::new);
//            throw new NotFoundException("Contract topilmadi");
    }
}
