package gdsc.sc.bsafe.web.controller;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.global.annotation.AuthenticationUser;
import gdsc.sc.bsafe.global.common.SuccessResponse;
import gdsc.sc.bsafe.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "회원 정보 조회 / 회원 탈퇴")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

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
