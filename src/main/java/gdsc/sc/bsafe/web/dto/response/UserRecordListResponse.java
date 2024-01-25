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
public class UserRecordListResponse {

    @Schema(description = "수리 신청 목록")
    private SliceResponse<RecordItemResponse> repair_record ;

    @Schema(description = "저장 목록")
    private SliceResponse<RecordItemResponse> saved_record ;

}

