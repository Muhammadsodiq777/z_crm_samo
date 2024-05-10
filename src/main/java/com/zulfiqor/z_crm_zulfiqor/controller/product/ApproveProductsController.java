package com.zulfiqor.z_crm_zulfiqor.controller.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.ConfirmStatus;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import com.zulfiqor.z_crm_zulfiqor.services.product.ApproveProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "1.0 Approve Products API")
@RequestMapping("/v1/approve")
public class ApproveProductsController {

    private final ApproveProductsService approveProductsService;

    // TODO: 11/13/2023 JPA ga o'tkazib chiqish krk

    @Operation(summary = "Tasdiqlanmagan mahsulotlar ro'yhatini olish")
    @GetMapping("all")
    public ResponseEntity<?> getAllUnconfirmedProducts() throws JsonProcessingException {
        BaseResponse<?> response = approveProductsService.getAllUnApprovedProducts();
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "transactionId bo'yicha tasdiqlanmagan mahsulotlarni detailed olish")
    @GetMapping("/details/{transactionId}")
    public ResponseEntity<?> getAllCategories(@RequestParam TradePlaceStatus status, @PathVariable(name = "transactionId") Long transactionId) throws JsonProcessingException {
        BaseResponse<?> response = approveProductsService.getAllUnApprovedProductDetails(status, transactionId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Mahsulotlarni tasdiqlash yoki rad etish")
    @PostMapping("/products")
    public ResponseEntity<?> approveProductsByTransactionId(@RequestParam ConfirmStatus status, @RequestParam Long transactionId, @RequestBody(required = false) String comment) {
        BaseResponse<?> response = approveProductsService.approveProductsByTransactionId(status, transactionId);
        return ResponseEntity.ok(response);
    }
}
