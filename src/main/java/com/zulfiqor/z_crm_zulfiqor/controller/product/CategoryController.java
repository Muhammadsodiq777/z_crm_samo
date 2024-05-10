package com.zulfiqor.z_crm_zulfiqor.controller.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.CategoryRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.ProductGroupRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.CategoryResponse;
import com.zulfiqor.z_crm_zulfiqor.services.product.CategoryService;
import com.zulfiqor.z_crm_zulfiqor.services.product.ProductGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "1.0: Category API")
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductGroupService productGroupService;

    @Operation(summary = "Categoriyalarni ro'yhati va uning child typelarini olish")
    @GetMapping("all")
    public ResponseEntity<?> getAllCategoriesAndChildType() {
        BaseResponse<?> response = categoryService.getAllCategoriesAndChildType();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "search category by name", description = "name ga categoriyaning ismi berib yuboriladi")
    @GetMapping("search/{name}")
    public ResponseEntity<?> searchByName(@PathVariable(name = "name") String name) {
        BaseResponse<List<CategoryResponse>> response = categoryService.searchByName(name);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Yangi categoriya qo'shish", description = "Agar qo'shish success bo'lsa 200 qaytaradi")
    @PostMapping("add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest categoryRequest) {
        BaseResponse<?> response = categoryService.addCategory(categoryRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Categoriyani tahrirlash", description ="categoriyani id raqami bo'yicha update qilish" )
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequest categoryRequest) {
        BaseResponse<?> response = categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete category", description = "Categoriyani o'chirish")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(name = "id") Long id) {
        BaseResponse<?> response = categoryService.deleteCategory(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Categorydan keyingi type add",
        description = "Categorydan keyingi product typelarni qo'shish | id Category ning id raqami")
    @PostMapping("add/type")
    public ResponseEntity<?> addParentType(@RequestParam Long categoryId, @RequestBody @Valid ProductGroupRequest request) {
        BaseResponse<?> response = productGroupService.addParentProductType(categoryId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Categorydan keyingi typeni update qilish")
    @PutMapping("update/type")
    public ResponseEntity<?> updateProductType(@RequestParam Long typeId, @RequestBody @Valid ProductGroupRequest request) {
        BaseResponse<?> response = productGroupService.updateParentProductType(typeId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Type ichidan qidirish")
    @GetMapping("search/type/{name}")
    public ResponseEntity<?> searchProductType(@PathVariable(name = "name") String name) {
        BaseResponse<?> response = productGroupService.searchParentProductType(name);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Type ichidan o'chirish")
    @DeleteMapping("delete/type/{id}")
    public ResponseEntity<?> deleteProductType(@PathVariable(name = "id") Long id) {
        BaseResponse<?> response = productGroupService.deleteParentProductType(id);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Guruh qo'shish",
        description = "Typega child qo'shish(CPU -> core i5) | categoryId category ning id raqami")
    @PostMapping("add/group")
    public ResponseEntity<?> addChildType(@RequestParam Long categoryId,
                                          @RequestBody @Valid ProductGroupRequest request) {
        BaseResponse<?> response = productGroupService.addChildProductGroup(categoryId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update group")
    @PutMapping("update/group")
    public ResponseEntity<?> updateChildType(@RequestParam Long groupId,
                                          @RequestBody @Valid ProductGroupRequest request) {
        BaseResponse<?> response = productGroupService.updateChildProductGroup(groupId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "TypeId bo'yicha guruhlarni nomi olish olish(Admin)")
    @GetMapping("group/{parentId}")
    public ResponseEntity<?> getProductGroup(@PathVariable(name = "parentId") Long parentId) {
        BaseResponse<?> response = productGroupService.getProductGroup(parentId);
        return ResponseEntity.ok(response);
    }

    // TODO: 11/13/2023 search ishlamayapti
    @Operation(summary = "Product guruh ichidan qidirish")
    @GetMapping("group/search")
    public ResponseEntity<?> searchFromProductGroup(@PathVariable(name = "name") String name) throws JsonProcessingException {
        BaseResponse<?> response = productGroupService.searchFromProductGroup(name);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Delete group")
    @DeleteMapping("delete/group/{groupId}")
    public ResponseEntity<?> deleteChildGroup(@PathVariable(name = "igroupIdd") Long groupId) {
        BaseResponse<?> response = productGroupService.deleteChildProductGroup(groupId);
        return ResponseEntity.ok(response);
    }



}
