package com.zulfiqor.z_crm_zulfiqor.controller.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.SellProduct;
import com.zulfiqor.z_crm_zulfiqor.services.product.OutProductService;
import com.zulfiqor.z_crm_zulfiqor.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "1.0 Mahsulotlarni sotish")
@RequestMapping("/v1/out-products")
public class OutProductController {

    private final OutProductService productService;
    private final SecurityUtils securityUtils;

    @Operation(summary = "Mahsulot sotish", description = "Omborda mavjud mahsulotlarni sotish apisi")
    @PostMapping("/sell")
    public ResponseEntity<?> sellProducts(@RequestBody SellProduct requestBody,
                                          @RequestHeader(name = "deviceId", required = false, defaultValue = "") String deviceId) {
        BaseResponse<?> response = productService.sellProducts(securityUtils.getCurrentUser(), requestBody, deviceId);
        return ResponseEntity.ok(response);
    }
}
