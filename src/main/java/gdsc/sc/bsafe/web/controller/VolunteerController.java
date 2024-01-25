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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    /*
        봉사자 신청 API
     */
    @Operation(summary = "봉사자 신청 API", description = "유저에게 봉사자 역할을 부여합니다. 요청 성공 시 user의 pk를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청에 성공했습니다.")
    })
    @PostMapping("/users")
    public ResponseEntity<SuccessResponse<?>> saveVolunteer(@AuthenticationUser User user,
                                                            @Valid @RequestBody VolunteerUserRequest volunteerUserRequest) {
        return SuccessResponse.created(volunteerService.saveVolunteer(user, volunteerUserRequest));
    }
}
