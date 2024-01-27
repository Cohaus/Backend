package gdsc.sc.bsafe.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepairIDResponse {

    @Schema(description = "저장 기록 pk", example = "1")
    private Long record_id;

    @Schema(description = "수리 기록 pk", example = "1")
    private Long repair_id;

}
