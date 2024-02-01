package gdsc.sc.bsafe.web.dto.request;

import gdsc.sc.bsafe.domain.enums.Authority;
import gdsc.sc.bsafe.domain.enums.VolunteerType;
import gdsc.sc.bsafe.global.annotation.EnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserInfoRequest {

    @Schema(description = "이름", example = "김지원")
    @Size(min = 2, message = "이름은 최소 2자 이상이어야 합니다.")
    @NotBlank(message = "이름은 필수입니다.")
    String name;

    @Schema(description = "전화번호", example = "01012345678")
    @Size(min = 9, max = 11, message = "전화번호는 9자 이상, 11자 이하여야 합니다.")
    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^\\d+$", message = "전화번호는 모두 숫자여야 합니다.")
    String tel;

    @Schema(description = "이메일", example = "user@gmail.com")
    @NotBlank(message = "이메일은 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$",
            message = "이메일 형식이 올바르지 않습니다.")
    String email;


}
