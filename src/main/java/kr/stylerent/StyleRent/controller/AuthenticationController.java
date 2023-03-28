package kr.stylerent.StyleRent.controller;

import kr.stylerent.StyleRent.dto.AuthenticationResponse;
import kr.stylerent.StyleRent.dto.LoginDto;
import kr.stylerent.StyleRent.dto.RegistrationDto;
import kr.stylerent.StyleRent.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @Autowired
    AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegistrationDto request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginDto request
    ) {
        return ResponseEntity.ok(service.login(request));
    }



}
