package com.shopping.shoppingapplication.rest;

import com.shopping.shoppingapplication.domain.dto.ProductCategoryDTO;
import com.shopping.shoppingapplication.exception.category.ProductCategoryIncorrectFieldException;
import com.shopping.shoppingapplication.exception.category.ProductCategoryNotFoundException;
import com.shopping.shoppingapplication.message.ResponseMessage;
import com.shopping.shoppingapplication.service.ProductCategoryService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ProductCategoryDTO productCategoryDTO) {

        try {
            Long id = productCategoryService.create(productCategoryDTO);

            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (ProductCategoryIncorrectFieldException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("")
    public ResponseEntity<?> update(@RequestParam Long id, @RequestBody ProductCategoryDTO productCategoryDTO) {
        try {
            ProductCategoryDTO updatedProductCategory = productCategoryService.update(id, productCategoryDTO);

            return ResponseEntity.status(HttpStatus.OK).body(updatedProductCategory);
        } catch (ProductCategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("show")
    public ResponseEntity<?> get(@RequestParam Long id) {
        try {
            ProductCategoryDTO productCategoryDTO = productCategoryService.get(id);

            return ResponseEntity.status(HttpStatus.OK).body(productCategoryDTO);
        } catch (ProductCategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<ProductCategoryDTO>> getAll() {
        List<ProductCategoryDTO> productCategories = productCategoryService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(productCategories);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("")
    public ResponseEntity<ResponseMessage> delete(@RequestParam Long id) {
        String message = "";
        HttpStatus statusCode;

        try {
            productCategoryService.delete(id);

            message = "You have successfully deleted category";
            statusCode = HttpStatus.OK;
        } catch (EmptyResultDataAccessException e) {
            message = "Oops, Category not found";
            statusCode = HttpStatus.NOT_FOUND;
        }catch (Exception e) {
            message = "Something went wrong";
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(statusCode).body(new ResponseMessage(message));
    }
}
