package com.zulfiqor.z_crm_zulfiqor.services.product.impl;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.filter.DateFilter;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.history.PageResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.statistics.*;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.InProducts;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.OutProducts;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.custom.CardDetailsCustomEntity;
import com.zulfiqor.z_crm_zulfiqor.model.enums.CardType;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.ConfirmStatus;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import com.zulfiqor.z_crm_zulfiqor.repository.product.CategoryRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.InProductRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.OutProductRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.TransactionRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.product.custom.StatisticsCustomRepository;
import com.zulfiqor.z_crm_zulfiqor.services.product.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final CategoryRepository categoryRepository;
    private final InProductRepository inProductRepository;
    private final OutProductRepository outProductRepository;
    private final TransactionRepository transactionRepository;
    private final StatisticsCustomRepository statisticsCustomRepository;


    @Override
    public BaseResponse<?> getCardsList() {
        List<CardTypeResponse> cardTypeResponses = new ArrayList<>();
        cardTypeResponses.add(new CardTypeResponse("Omborda mavjud mahsulotlar", CardType.PRESENT,
                getCardSums(CardType.PRESENT)));

        cardTypeResponses.add(new CardTypeResponse("Sotib olingan", CardType.BOUGHT,
                getCardSums(CardType.BOUGHT)));

        cardTypeResponses.add(new CardTypeResponse("Sotilgan", CardType.SOLD,
                getCardSums(CardType.SOLD)));

        return BaseResponse.success(cardTypeResponses);
    }

    private CardDetailsResponse getCardSums(CardType type){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date firstDayOfMonth = calendar.getTime();

        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        calendar.add(Calendar.MILLISECOND, -1);
        Date lastDayOfMonth = calendar.getTime();

        switch (type){
            case PRESENT -> {
                List<InProducts> allInProducts
                        = inProductRepository.findAllByActiveIsTrueAndCreatedDateBetween(firstDayOfMonth, lastDayOfMonth);
                if(allInProducts.isEmpty())
                    return new CardDetailsResponse(0L, new BigDecimal(0));
                long totalProducts = 0;
                BigDecimal totalPrice = BigDecimal.ZERO;
                for (InProducts product : allInProducts) {
                    totalProducts = (product.getQuantity() - product.getSoldQuantity());
                    BigDecimal productPrice = BigDecimal.valueOf(product.getPrice());
                    totalPrice = totalPrice.add(productPrice);
                }
                return new CardDetailsResponse(totalProducts, totalPrice);
            }

            case BOUGHT ->{
                List<InProducts> allInProducts
                        = inProductRepository.findAllByActiveIsTrueAndCreatedDateBetween(firstDayOfMonth, lastDayOfMonth);
                if(allInProducts.isEmpty())
                    return new CardDetailsResponse(0L, new BigDecimal(0));
                long totalProducts = 0;
                BigDecimal totalPrice = BigDecimal.ZERO;
                for (InProducts product : allInProducts) {
                    totalProducts = (product.getQuantity());
                    BigDecimal productPrice = BigDecimal.valueOf(product.getPrice());
                    totalPrice = totalPrice.add(productPrice);
                }
                return new CardDetailsResponse(totalProducts, totalPrice);
            }

            case SOLD -> {
                List<OutProducts> all = outProductRepository.findAllByActiveIsTrueAndCreatedDateBetween(firstDayOfMonth, lastDayOfMonth);
                if(all.isEmpty())
                    return new CardDetailsResponse(0L, new BigDecimal(0));
                long totalProducts = 0;
                BigDecimal totalPrice = BigDecimal.ZERO;

                for (OutProducts product : all) {
                    totalProducts = (product.getQuantity());
                    BigDecimal productPrice = BigDecimal.valueOf(product.getPrice());
                    totalPrice = totalPrice.add(productPrice);
                }
                return new CardDetailsResponse(totalProducts, totalPrice);
            }
        }
        return new CardDetailsResponse(0L, new BigDecimal(0));
    }

    @Override
    public BaseResponse<?> getCardDetailsList(DateFilter filter) {

        Page<CardDetailsCustomEntity> card = statisticsCustomRepository.getPresentProductsListForCard(filter);

        switch (filter.getCardType()){
            case PRESENT ->{
//          present
            }
            case BOUGHT ->{
//--bought
            }
            case SOLD ->{
//---sold
            }
            default -> {
// ---default
            }
        }

        CardDetailsTotal total = new CardDetailsTotal();
        total.setTotalProducts(124345L);
        total.setTotalSums(123213D);
        PageResponse response = new PageResponse(card.getContent());
        response.setTotalSums(total);
        response.setSize(card.getSize());
        response.setPage(filter.getPage());
        response.setTotalPage(card.getTotalPages());
        response.setTotalElements(card.getTotalElements());
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<?> getPieChartDetails(TradePlaceStatus status, Integer year) {
        List<PieChartDetailsResponse> detailsResponses = Arrays.asList(
                new PieChartDetailsResponse(1,"Yanvar", 123000D),
                new PieChartDetailsResponse(2,"Fevral", 323500D),
                new PieChartDetailsResponse(3,"Mart", 15480D),
                new PieChartDetailsResponse(4,"Aprel", 123000D),
                new PieChartDetailsResponse(5,"May", 123000D),
                new PieChartDetailsResponse(6,"Iyun", 123000D),
                new PieChartDetailsResponse(7,"Iyul", 123000D),
                new PieChartDetailsResponse(8,"Avgust", 123000D),
                new PieChartDetailsResponse(9,"Sentabr", 123000D),
                new PieChartDetailsResponse(10,"Oktabr", 123000D),
                new PieChartDetailsResponse(11,"Noyabr", 123000D),
                new PieChartDetailsResponse(12,"Dekabr", 123000D)
        );
        return BaseResponse.success(detailsResponses);
    }
}
