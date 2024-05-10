package com.zulfiqor.z_crm_zulfiqor.controller.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.ProductNamesRequest;
import com.zulfiqor.z_crm_zulfiqor.services.product.ProductService;
import com.zulfiqor.z_crm_zulfiqor.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "1.0 Mahsulotlar ro'yhati")
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;
    private final SecurityUtils securityUtils;

    @Operation(summary = "Mahsulot nomini qo'shish")
    @PostMapping("/add")
    public ResponseEntity<?> addProductNames(@RequestBody @Valid ProductNamesRequest request) {
        BaseResponse<?> response = productService.addProductNames(securityUtils.getCurrentUser(), request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Guruh id raqami orqali olish", description = "Guruh id raqami (e.g: Core i5 id) beriladi")
    @GetMapping("/{parentId}/all")
    private ResponseEntity<?> getId(@PathVariable(name = "parentId") Long parentId){
        BaseResponse<?> response = productService.getByProductGroupId(parentId);
        return ResponseEntity.ok(response);
    }
}
