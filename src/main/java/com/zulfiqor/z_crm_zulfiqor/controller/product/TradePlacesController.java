package com.zulfiqor.z_crm_zulfiqor.controller.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.TradePlaceRequest;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import com.zulfiqor.z_crm_zulfiqor.services.product.TradePlacesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "1. Sotish va sotib olish yeri")
@RequestMapping("/v1/trade")
public class TradePlacesController {

    private final TradePlacesService service;

    @Operation(summary = "Sotish va Olish yerlarni qidirish",
            description = "Buy | Sell")
    @GetMapping("/search/{status}/{name}")
    public ResponseEntity<?> searchPurchasePlaces(@PathVariable(name = "status")TradePlaceStatus status,
                                                  @PathVariable(name = "name") String name) {
        BaseResponse<?> response = service.searchTradePlaces(status, name);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<?> addTradePlace(@RequestBody @Valid TradePlaceRequest request) {
        BaseResponse<?> response = service.addTradePlace(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Trade yerlarini olish",
            description = "Buy | Sell")
    @GetMapping("/get-all/places/by-status")
    public ResponseEntity<?> getAllTradePlacesByStatus(@RequestParam TradePlaceStatus status) {
        BaseResponse<?> response = service.getAllTradePlacesByStatus(status);
        return ResponseEntity.ok(response);
    }

}
