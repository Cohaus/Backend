package gdsc.sc.bsafe.controller;

import gdsc.sc.bsafe.dto.request.LoginRequest;
import gdsc.sc.bsafe.dto.request.SignUpRequest;
import gdsc.sc.bsafe.dto.response.LoginResponse;
import gdsc.sc.bsafe.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
