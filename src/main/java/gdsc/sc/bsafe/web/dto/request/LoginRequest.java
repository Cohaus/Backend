package gdsc.sc.bsafe.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @Schema(description = "아이디", example = "id1234")
        @NotBlank(message = "아이디를 입력해주세요.")
        String id,

        @Schema(description = "비밀번호", example = "pwd1234!")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}
