package com.yagato.HololiveAPI.controller;

import com.yagato.HololiveAPI.model.Authentication;
import com.yagato.HololiveAPI.model.Register;
import com.yagato.HololiveAPI.response.AuthenticationResponse;
import com.yagato.HololiveAPI.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Register register) {

        return ResponseEntity.ok(authenticationService.register(register));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Authentication authentication) {

        return ResponseEntity.ok(authenticationService.authenticate(authentication));

    }

}
