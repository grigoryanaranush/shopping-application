package com.shopping.shoppingapplication.rest;

import com.shopping.shoppingapplication.domain.dto.ProductCommentDTO;
import com.shopping.shoppingapplication.exception.product.ProductNotFoundException;
import com.shopping.shoppingapplication.exception.user.UserNotFoundException;
import com.shopping.shoppingapplication.message.ResponseMessage;
import com.shopping.shoppingapplication.service.RateProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RateProductController {

    private final RateProductService rateProductService;

    public RateProductController(RateProductService rateProductService) {
        this.rateProductService = rateProductService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/rate-product")
    public ResponseEntity<ResponseMessage> rateProduct(@RequestBody ProductCommentDTO productCommentDTO) {
        try {
            rateProductService.rateProduct(productCommentDTO);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Something went wrong"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("You have rated product"));
    }
}
