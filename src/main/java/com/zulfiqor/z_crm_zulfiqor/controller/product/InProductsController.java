package com.zulfiqor.z_crm_zulfiqor.controller.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.InProductsRequest;
import com.zulfiqor.z_crm_zulfiqor.services.product.InProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "1.0 Omborda mavjud mahsulotlar")
@RequestMapping("/v1/in-products/")
public class InProductsController {

    private final InProductsService inProductsService;

    @Operation(summary = "Omborga mahsulot qo'shish")
    @PostMapping("add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid InProductsRequest productRequest,
                                        @RequestHeader(name = "deviceId", required = false, defaultValue = "") String deviceId) {
        BaseResponse<?> response = inProductsService.addProduct(productRequest, deviceId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Mahsulotlarni tahrirlash")
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id,
                                           @RequestBody @Valid InProductsRequest productListRequest) {
        return ResponseEntity.ok(inProductsService.updateProduct(id, productListRequest));
    }

    @Operation(summary = "Omborda mavjud mahsulotlar ro'yhatini olish")
    @GetMapping("all")
    public ResponseEntity<?> getAllPresentProducts(@RequestParam(name = "parentId") Long parentId) {
        BaseResponse<?> response = inProductsService.getAllPresentProductsInWarehouse(parentId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Name va code bo'yicha qidirish")
    @GetMapping("search")
    public ResponseEntity<?> searchProductByNameOrCode(@RequestParam(name = "param") String param) {
        BaseResponse<?> response = inProductsService.searchProductByNameOrCode(param);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Omborda mavjud mahsulotlar ro'yhatini olish")
    @GetMapping("get/{inProductId}")
    public ResponseEntity<?> getInProductId(@PathVariable(name = "inProductId") Long inProductId) {
        BaseResponse<?> response = inProductsService.getInProductById(inProductId);
        return ResponseEntity.ok(response);
    }
}
