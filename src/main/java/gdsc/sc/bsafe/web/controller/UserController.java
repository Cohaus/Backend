package gdsc.sc.bsafe.web.controller;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.global.annotation.AuthenticationUser;
import gdsc.sc.bsafe.global.common.SuccessResponse;
import gdsc.sc.bsafe.service.UserService;
import gdsc.sc.bsafe.web.dto.request.UpdateUserInfoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API", description = "회원정보 수정 / 회원 탈퇴")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원정보 수정 API", description = "요청 성공 시 회원 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")
    })
    @PatchMapping("/info")

    public ResponseEntity<SuccessResponse<?>> updateUserInfo(@AuthenticationUser User user,
                                                             @Valid @RequestBody UpdateUserInfoRequest request) {
        return SuccessResponse.ok(userService.updateUserInfo(user, request));
    }

    @Operation(summary = "회원 탈퇴 API", description = "요청 성공 시 DB에서 회원 데이터를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")
    })
    @DeleteMapping("/withdraw")
    public ResponseEntity<SuccessResponse<?>> withdrawUser(@AuthenticationUser User user) {
        userService.withdrawUser(user.getUserId());

        return SuccessResponse.ok(null);
    }
}
