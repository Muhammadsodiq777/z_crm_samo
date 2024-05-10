package com.zulfiqor.z_crm_zulfiqor.services.product.impl;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.TradePlaceRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.SaveResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.TradePlaceResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.TradePlaces;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import com.zulfiqor.z_crm_zulfiqor.repository.product.TradePlacesRepository;
import com.zulfiqor.z_crm_zulfiqor.services.product.TradePlacesService;
import com.zulfiqor.z_crm_zulfiqor.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradePlacesServiceImpl implements TradePlacesService {

    private final TradePlacesRepository tradePlacesRepository;
    private final SecurityUtils securityUtils;

    @Override
    public BaseResponse<?> searchTradePlaces(TradePlaceStatus status, String name) {

        List<TradePlaces> tradePlaces = tradePlacesRepository.findByNameContainingIgnoreCaseAndTradeStatusAndActiveTrue(name, status);
        if (tradePlaces.isEmpty())
            return BaseResponse.error(-1, "Topilmadi");

        List<TradePlaceResponse> responses = new ArrayList<>();

        for (TradePlaces tradePlace : tradePlaces) {
            TradePlaceResponse response = new TradePlaceResponse(tradePlace.getId(), tradePlace.getName());
            responses.add(response);
        }
        return BaseResponse.success(responses);
    }


    @Override
    public BaseResponse<?> addTradePlace(TradePlaceRequest request) {
        TradePlaces tradePlaces = new TradePlaces();
        tradePlaces.setTradeStatus(request.status());
        tradePlaces.setName(request.name());
        tradePlaces.setDescription(request.description());
        tradePlaces.setActive(true);
        tradePlaces.setUpdateBy(securityUtils.getCurrentUser().getId());
        tradePlacesRepository.save(tradePlaces);
        return BaseResponse.success(new SaveResponse(tradePlaces.getId()));
    }

    @Override
    public BaseResponse<?> getAllTradePlacesByStatus(TradePlaceStatus status) {
        List<TradePlaceResponse> list = new ArrayList<>();
        List<TradePlaces> tradeStatus = tradePlacesRepository.findAllByActiveIsTrueAndTradeStatus(status);

        if (!tradeStatus.isEmpty()) {
            for (TradePlaces p : tradeStatus) {
                TradePlaceResponse tr = new TradePlaceResponse(p.getId(), p.getName());
                list.add(tr);
            }
        }
        return BaseResponse.success(!list.isEmpty() ? list : new ArrayList<>());
    }
}
