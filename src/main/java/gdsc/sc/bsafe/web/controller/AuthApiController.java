package gdsc.sc.bsafe.web.controller;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.global.annotation.AuthenticationUser;
import gdsc.sc.bsafe.global.common.SuccessResponse;
import gdsc.sc.bsafe.service.AuthService;
import gdsc.sc.bsafe.web.dto.request.LoginRequest;
import gdsc.sc.bsafe.web.dto.request.SignUpRequest;
import gdsc.sc.bsafe.web.dto.response.LoginResponse;
import gdsc.sc.bsafe.web.dto.response.SavedRecordResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth API", description = "회원가입 / 로그인 / 로그아웃 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {

    private final AuthService authService;

    @Operation(summary = "회원가입 API", description = "회원가입 필드 값을 받습니다. 요청 성공 시 user의 pk를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.", useReturnTypeSchema = true,
                    content = @Content(schema = @Schema(implementation = Long.class)))
    })
    @PostMapping("/sign-up")
    public ResponseEntity<SuccessResponse<?>> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        Long userId = authService.signUp(signUpRequest);

        return SuccessResponse.created(userId);
    }

    @Operation(summary = "로그인 API", description = "로그인 필드 값을 받습니다. 요청 성공 시 user의 pk와 발급된 token을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.", useReturnTypeSchema = true,
                    content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<?>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);

        return SuccessResponse.ok(loginResponse);
    }

    @Operation(summary = "로그아웃 API", description = "요청 성공 시 token을 폐기해주세요.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")
    })
    @DeleteMapping("/logout")
    public ResponseEntity<SuccessResponse<?>> logout(@AuthenticationUser User user) {
        authService.logout(user.getUserId());

        return SuccessResponse.ok(null);
    }
}
