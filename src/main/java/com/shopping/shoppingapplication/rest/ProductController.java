package com.shopping.shoppingapplication.rest;

import com.shopping.shoppingapplication.domain.dto.ProductDTO;
import com.shopping.shoppingapplication.domain.filter.FilterProduct;
import com.shopping.shoppingapplication.exception.product.ProductIncorrectFieldException;
import com.shopping.shoppingapplication.exception.product.ProductNotFoundException;
import com.shopping.shoppingapplication.message.ResponseMessage;
import com.shopping.shoppingapplication.service.ProductService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create( @RequestBody ProductDTO productDTO) {

        try {
            Long id = productService.create(productDTO);

            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (ProductIncorrectFieldException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("")
    public ResponseEntity<?> update(@RequestParam Long id, @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProduct = productService.update(id, productDTO);

            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("show")
    public ResponseEntity<?> get(@RequestParam Long id) {
        try {
            ProductDTO productDTO = productService.get(id);

            return ResponseEntity.status(HttpStatus.OK).body(productDTO);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<ProductDTO>> getAll() {
        List<ProductDTO> products = productService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("")
    public ResponseEntity<ResponseMessage> delete(@RequestParam Long id) {
        String message = "";
        HttpStatus statusCode;

        try {
            productService.delete(id);

            message = "You have successfully deleted product";
            statusCode = HttpStatus.OK;
        } catch (EmptyResultDataAccessException e) {
            message = "Oops, Product not found";
            statusCode = HttpStatus.NOT_FOUND;
        }catch (Exception e) {
            message = "Something went wrong";
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(statusCode).body(new ResponseMessage(message));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/filter")
    public List<ProductDTO> filter( FilterProduct filterProduct) {
        return productService.filter(filterProduct);
    }
}
