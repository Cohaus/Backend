package gdsc.sc.bsafe.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerUserRequest {

    @Schema(description = "봉사자 유형", example = "개인 -> SINGLE / 조직 -> ORGANIZATION")
    @NotBlank
    String type;

    @Schema(description = "봉사 조직 이름", example = "가천대학교 봉사 동아리")
    String organization_name;

}
