package com.shopping.shoppingapplication.rest;

import com.shopping.shoppingapplication.config.security.TokenProvider;
import com.shopping.shoppingapplication.domain.dto.AuthToken;
import com.shopping.shoppingapplication.domain.dto.LoginPayload;
import com.shopping.shoppingapplication.domain.dto.UserDTO;
import com.shopping.shoppingapplication.exception.user.UserNotFoundException;
import com.shopping.shoppingapplication.message.ResponseMessage;
import com.shopping.shoppingapplication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenUtil;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager,
                          TokenProvider jwtTokenUtil,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/block-user")
    public ResponseEntity<ResponseMessage> blockUser(@RequestParam Long userId) {
        try {
            userService.blockUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("User is blocked"));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/unblock-user")
    public ResponseEntity<ResponseMessage> unblockUser(@RequestParam Long userId) {
        try {
            userService.unblockUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("User is unblocked"));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
        }
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody LoginPayload loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);

        return ResponseEntity.ok(new AuthToken(token));
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ResponseEntity<Long> saveUser(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.save(user));
    }
}
