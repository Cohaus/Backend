package gdsc.sc.bsafe.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponse {

    @Schema(description = "user의 pk", example = "1")
    private Long user_id;

    @Schema(description = "이름", example = "김지원")
    private String name;

}
