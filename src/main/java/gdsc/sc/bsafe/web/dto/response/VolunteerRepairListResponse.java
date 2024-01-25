package gdsc.sc.bsafe.web.dto.response;

import gdsc.sc.bsafe.web.dto.common.SliceResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerRepairListResponse {

    @Schema(description = "진행 봉사목록")
    private SliceResponse<RepairItemResponse> proceeding_repair ;

    @Schema(description = "완료 봉사목록")
    private SliceResponse<RepairItemResponse> complete_repair ;

}
