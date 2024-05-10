package com.zulfiqor.z_crm_zulfiqor.controller.history;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.filter.HistoryFilter;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import com.zulfiqor.z_crm_zulfiqor.services.product.HistoryService;
import com.zulfiqor.z_crm_zulfiqor.services.product.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "1.0: Kirim-Chiqim tarixi")
@RequestMapping("/api/v1/history")
public class HistoryController {

    private final HistoryService historyService;
    private final TransactionService transactionService;

    @Operation(summary = "Barcha sotib olingan va sotilgan mahsulotlar")
    @PostMapping("all")
    public BaseResponse<?> getAllHistory(@RequestParam TradePlaceStatus status, @RequestBody(required = false) HistoryFilter filter) {
        return historyService.getHistory(status, filter);
    }

    @GetMapping("detail/{transactionId}")
    @Operation(summary = "Sotib olingan yoki sotilgan mahsulotni id bo'yicha olish")
    public ResponseEntity<?> getHistoryDetails(@RequestParam TradePlaceStatus status, @PathVariable(name = "transactionId") Long transactionId) {
        BaseResponse<?> response = historyService.getHistoryDetails(status, transactionId);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("transaction/{id}")
//    @Operation(summary = "sotib olingan mahsulot yoki sotilgan mahsulotlarni partiyasini olish Transaction Id bo'yicha")
//    public ResponseEntity<?> getTransaction(@PathVariable("id") Long transactionId) {
//        BaseResponse<?> response = transactionService.getTransactionById(transactionId);
//        return ResponseEntity.ok(response);
//    }
}
