package com.zulfiqor.z_crm_zulfiqor.repository.product;

import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.TradePlaces;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradePlacesRepository extends JpaRepository<TradePlaces, Long> {

    TradePlaces findByIdAndTradeStatus(Long id, TradePlaceStatus status);

    TradePlaces searchTradePlacesByNameAndTradeStatus(String search, TradePlaceStatus status);

    List<TradePlaces> findAllByActiveIsTrueAndTradeStatus(TradePlaceStatus status);

    List<TradePlaces> findByNameContainingIgnoreCaseAndTradeStatusAndActiveTrue(String search, TradePlaceStatus status);

}
