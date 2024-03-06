package dicegame.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dicegame.api.dao.request.SignInRequest;
import dicegame.api.dao.request.SignUpRequest;
import dicegame.api.dao.response.JwtAuthenticationResponse;
import dicegame.api.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    
    private final AuthenticationService authenticationService;
   
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
