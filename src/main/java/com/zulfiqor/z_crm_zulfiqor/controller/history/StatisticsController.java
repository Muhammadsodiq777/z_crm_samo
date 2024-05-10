package com.zulfiqor.z_crm_zulfiqor.controller.history;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.filter.DateFilter;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import com.zulfiqor.z_crm_zulfiqor.services.product.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "1.0: Statistics API")
@RequestMapping("/v1/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(summary = "Categoriyalarni ro'yhatini adminka uchun olish")
    @GetMapping("card-list")
    public ResponseEntity<?> getCardsList() {
        BaseResponse<?> response = statisticsService.getCardsList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cartalarni batafsil malumotini olish")
    @PostMapping("card/details/list")
    public ResponseEntity<?> getCardDetailsList(@RequestBody @Valid DateFilter filter) {
        BaseResponse<?> response = statisticsService.getCardDetailsList(filter);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Pie Chart Malumotlarini olish")
    @GetMapping("pie-chart")
    public ResponseEntity<?> getPieChart(@RequestParam TradePlaceStatus status, Integer year) {
        BaseResponse<?> response = statisticsService.getPieChartDetails(status, year);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Line-graph malumotlarini olish")
    @GetMapping("line-graph")
    public ResponseEntity<?> getLineGraph(@RequestParam TradePlaceStatus status, Integer year) {
        BaseResponse<?> response = statisticsService.getPieChartDetails(status, year);
        return ResponseEntity.ok(response);
    }

}
