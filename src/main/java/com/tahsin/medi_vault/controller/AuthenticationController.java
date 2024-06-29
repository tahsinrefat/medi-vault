package com.tahsin.medi_vault.controller;

import com.tahsin.medi_vault.config.AuthenticationService;
import com.tahsin.medi_vault.dto.authentication.AuthenticationRequest;
import com.tahsin.medi_vault.dto.authentication.AuthenticationResponse;
import com.tahsin.medi_vault.dto.register.RegisterRequest;
import com.tahsin.medi_vault.dto.register.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
      @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
