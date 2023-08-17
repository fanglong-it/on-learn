package fpt.fall23.onlearn.controller;

import fpt.fall23.onlearn.config.OpenApiConfig;
import fpt.fall23.onlearn.dto.account.response.AccountDetailResponse;
import fpt.fall23.onlearn.dto.auth.request.AuthenticationRequest;
import fpt.fall23.onlearn.dto.auth.request.GoogleLoginRequest;
import fpt.fall23.onlearn.dto.auth.response.AuthenticationResponse;
import fpt.fall23.onlearn.entity.Account;
import fpt.fall23.onlearn.enums.ResponseCode;
import fpt.fall23.onlearn.exception.ApiException;
import fpt.fall23.onlearn.mappings.AccountMapper;
import fpt.fall23.onlearn.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AccountMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        var response = authenticationService.authentication(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/google")
    public ResponseEntity<AuthenticationResponse> loginGoogle(@Valid @RequestBody GoogleLoginRequest request) {
        var response = authenticationService.googleLogin(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refresh-token")
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request) {
        final String BEARER = "Bearer ";
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            throw new ApiException("Invalid refresh token");
        }

        final String jwt = authHeader.substring(BEARER.length());
        var response = authenticationService.refreshToken(jwt, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Transactional
    public ResponseEntity<AccountDetailResponse> getProfile() {
        Account acc = authenticationService.getCurrentAuthenticatedAccount()
                .orElseThrow(() -> new ApiException(ResponseCode.UNAUTHORIZED));

        AccountDetailResponse accResponse = mapper.toDetailResponse(acc);
        return ResponseEntity.ok(accResponse);
    }
}
