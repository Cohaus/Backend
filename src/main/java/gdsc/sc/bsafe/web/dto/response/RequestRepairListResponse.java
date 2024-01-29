package gdsc.sc.bsafe.web.dto.response;

import gdsc.sc.bsafe.web.dto.common.SliceResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;

@Schema
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class RequestRepairListResponse {

    @Schema(description = "전체 수리 요청 리스트")
    private SliceResponse<RequestRepairResponse> request_repairs;

}
