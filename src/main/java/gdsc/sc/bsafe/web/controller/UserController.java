package gdsc.sc.bsafe.web.controller;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.global.annotation.AuthenticationUser;
import gdsc.sc.bsafe.global.common.SuccessResponse;
import gdsc.sc.bsafe.service.RecordService;
import gdsc.sc.bsafe.service.UserService;
import gdsc.sc.bsafe.web.dto.response.UserRecordListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my-page")
public class UserController {
    private final RecordService recordService;
    private final UserService userService;
    /*
        프로필 유저 정보 조회
     */
    @Operation(summary = "마이페이지 - 유저 정보 조회 API", description = "유저의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")
    })
    @GetMapping()
    public ResponseEntity<SuccessResponse<?>> getUserInfo(@AuthenticationUser User user){
        return SuccessResponse.ok(userService.getUserInfo(user));
    }

    /*
    프로필 기록 목록 조회하기
    */
    @Operation(summary = "마이페이지 - 기록 목록 조회 API", description = "유저의 지기록 목록들을 보여줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")
    })
    @GetMapping("/records")
    public ResponseEntity<SuccessResponse<?>> getMyRecordList(@AuthenticationUser User user){
        UserRecordListResponse userRecordListResponse = recordService.getUserRecords(user);
        return SuccessResponse.ok(userRecordListResponse);
    }
}
