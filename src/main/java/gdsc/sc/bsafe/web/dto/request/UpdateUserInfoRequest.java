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

    @Schema(description = "사용자 유형 (일반 사용자 -> USER / 봉사자 -> VOLUNTEER)", example = "VOLUNTEER")
    @EnumValid(enumClass = Authority.class, message = "유효하지 않은 권한(역할)입니다.")
    @NotBlank(message = "사용자 유형은 필수입니다.")
    String user_authority;

    @Schema(description = "봉사자 유형 (1. 개인 -> SINGLE / 2. 조직 -> ORGANIZATION / 3. 일반 유저(USER)일 경우 -> null)", example = "ORGANIZATION")
    @EnumValid(enumClass = VolunteerType.class, message = "유효하지 않은 봉사자 타입입니다.")
    String volunteer_type;

    @Schema(description = "봉사 조직 이름 (일반 유저(USER)이거나 개인 봉사자(SINGLE)일 경우 -> null", example = "GDSC CoHaus")
    String organization_name;

}
