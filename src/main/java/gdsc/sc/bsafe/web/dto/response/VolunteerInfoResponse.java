package gdsc.sc.bsafe.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerInfoResponse {

    @Schema(description = "봉사자 유형", example = "개인 -> SINGLE / 조직 -> ORGANIZATION")
    String type;

    @Schema(description = "봉사 조직 이름, 개인 봉사자일 시 null 값이 반환됩니다.", example = "가천대학교 봉사 동아리")
    String organization_name;
}
