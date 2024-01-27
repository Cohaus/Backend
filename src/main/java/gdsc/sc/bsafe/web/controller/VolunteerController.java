package gdsc.sc.bsafe.web.controller;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.global.annotation.AuthenticationUser;
import gdsc.sc.bsafe.global.common.SuccessResponse;
import gdsc.sc.bsafe.service.VolunteerService;
import gdsc.sc.bsafe.web.dto.request.VolunteerUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    /*
        봉사자 신청 API
     */
    @Operation(summary = "설정 - 봉사자 신청 API", description = "유저에게 봉사자 역할을 부여합니다. 요청 성공 시 user의 pk를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청에 성공했습니다.")
    })
    @PostMapping("/users")
    public ResponseEntity<SuccessResponse<?>> saveVolunteer(@AuthenticationUser User user,
                                                            @Valid @RequestBody VolunteerUserRequest volunteerUserRequest) {
        return SuccessResponse.created(volunteerService.saveVolunteer(user, volunteerUserRequest));
    }

    /*
        수리 요청 자원봉사 신청 API
     */
    @Operation(summary = "수리 신청 정보 - 수리 자원봉사 신청 API", description = "요청 성공 시 repair(수리 요청)의 pk를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")
    })
    @PatchMapping("/repairs/{repairId}")
    public ResponseEntity<SuccessResponse<?>> volunteerRepair(@AuthenticationUser User user,
                                                              @PathVariable(name = "repairId") Long repairId) {
        return SuccessResponse.created(volunteerService.volunteerRepair(user, repairId));
    }

    /*
        봉사자 프로필 - 봉사 목록 조회하기
     */
    @Operation(summary = "프로필 - 봉사 목록 전체 조회 API", description = "봉사자 유저의 Profile 메뉴에서 전체 봉사 목록을 보여줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")
    })
    @GetMapping("/repairs")
    public ResponseEntity<SuccessResponse<?>> getVolunteerRepairList(@AuthenticationUser User user){
        return SuccessResponse.ok(volunteerService.getVolunteerRepairList(user));
    }

}
