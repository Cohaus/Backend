package gdsc.sc.bsafe.web.controller;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.global.annotation.AuthenticationUser;
import gdsc.sc.bsafe.global.common.SuccessResponse;
import gdsc.sc.bsafe.service.RepairService;
import gdsc.sc.bsafe.web.dto.response.RequestRepairListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapController {

    private final RepairService repairService;

    @Operation(summary = "Map - 전체 수리 요청 리스트 반환", description = "map 화면에서 보여지는 수리 요청들을 리스트로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.", useReturnTypeSchema = true,
                    content = @Content(schema = @Schema(implementation = RequestRepairListResponse.class)))
    })
    @GetMapping("/repairs")
    public ResponseEntity<SuccessResponse<?>> getRepairList(@AuthenticationUser User user){
        return SuccessResponse.ok(repairService.getRepairList(user));
    }

}
