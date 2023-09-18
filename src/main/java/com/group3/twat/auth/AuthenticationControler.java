package com.group3.twat.auth;

import com.group3.twat.auth.templates.AuthenticationRequest;
import com.group3.twat.auth.templates.AuthenticationResponse;
import com.group3.twat.auth.templates.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationControler {
private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse>register(
             @RequestBody RegisterRequest request
         ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse>authenticate(
            @RequestBody AuthenticationRequest request
    ){
     return   ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/token")
    public void refreshToken(){
        //TODO return access token
    }



}

