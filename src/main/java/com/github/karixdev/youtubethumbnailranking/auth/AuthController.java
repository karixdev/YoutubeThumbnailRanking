package com.github.karixdev.youtubethumbnailranking.auth;

import com.github.karixdev.youtubethumbnailranking.auth.payload.request.RegisterRequest;
import com.github.karixdev.youtubethumbnailranking.shared.payload.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(
            @Valid @RequestBody RegisterRequest payload
    ) {
        return new ResponseEntity<>(
                service.registerNewUser(payload),
                HttpStatus.CREATED);
    }
}