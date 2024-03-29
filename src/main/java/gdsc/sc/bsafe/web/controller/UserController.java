package gdsc.sc.bsafe.web.controller;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.global.annotation.AuthenticationUser;
import gdsc.sc.bsafe.global.common.SuccessResponse;
import gdsc.sc.bsafe.service.RecordService;
import gdsc.sc.bsafe.service.UserService;
import gdsc.sc.bsafe.web.dto.request.UpdateUserInfoRequest;
import gdsc.sc.bsafe.web.dto.response.UpdateUserInfoResponse;
import gdsc.sc.bsafe.web.dto.response.UserInfoResponse;
import gdsc.sc.bsafe.web.dto.response.UserRecordListResponse;
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

@Tag(name = "User my-page API", description = "회원정보 조회 / 기록 목록 조회 / 회원정보 수정 / 회원 탈퇴")
@RestController
@RequiredArgsConstructor
@RequestMapping("/my-page")
public class UserController {

    private final UserService userService;
    private final RecordService recordService;

    /*
        프로필 유저 정보 조회
     */
    @Operation(summary = "마이페이지 - 유저 정보 조회 API", description = "유저의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.", useReturnTypeSchema = true,
                    content = @Content(schema = @Schema(implementation = UserInfoResponse.class)))
    })
    @GetMapping("")
    public ResponseEntity<SuccessResponse<?>> getUserInfo(@AuthenticationUser User user){
        return SuccessResponse.ok(userService.getUserInfo(user));
    }

    /*
        프로필 기록 목록 조회하기
    */
    @Operation(summary = "마이페이지 - 기록 목록 조회 API", description = "유저의 기록 목록들을 보여줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.", useReturnTypeSchema = true,
                    content = @Content(schema = @Schema (implementation = UserRecordListResponse.class)))
    })
    @GetMapping("/records")
    public ResponseEntity<SuccessResponse<?>> getMyRecordList(@AuthenticationUser User user){
        UserRecordListResponse userRecordListResponse = recordService.getUserRecords(user);
        return SuccessResponse.ok(userRecordListResponse);
    }

    /*
        프로필 회원정보 수정
     */
    @Operation(summary = "회원정보 수정 API", description = "요청 성공 시 회원 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.", useReturnTypeSchema = true,
                    content = @Content(schema = @Schema (implementation = UpdateUserInfoResponse.class)))
    })
    @PatchMapping("/info")
    public ResponseEntity<SuccessResponse<?>> updateUserInfo(@AuthenticationUser User user,
                                                             @Valid @RequestBody UpdateUserInfoRequest request) {
        return SuccessResponse.ok(userService.updateUserInfo(user, request));
    }

    /*
        프로필 회원탈퇴
     */
    @Operation(summary = "회원 탈퇴 API", description = "요청 성공 시 DB에서 회원 데이터를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.", useReturnTypeSchema = true,
                    content = @Content(schema = @Schema (implementation = Void.class)))
    })
    @DeleteMapping("/withdraw")
    public ResponseEntity<SuccessResponse<?>> withdrawUser(@AuthenticationUser User user) {
        userService.withdrawUser(user);

        return SuccessResponse.ok(null);
    }

}
