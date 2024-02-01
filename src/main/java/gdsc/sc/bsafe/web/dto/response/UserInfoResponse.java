package gdsc.sc.bsafe.web.dto.response;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.enums.VolunteerType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    @Schema(description = "유저 pk", example = "1")
    private Long user_id;

    @Schema(description = "이름", example = "김지원")
    private String name;

    @Schema(description = "아이디", example = "id1234")
    private String id;

    @Schema(description = "전화번호", example = "010-1111-1111")
    private String tel;

    @Schema(description = "이메일", example = "cohaus@gmail.com")
    private String email;

    @Schema(description = "봉사자 유형 (일반 유저(USER)인 경우 null이 반환됩니다.)", example = "ORGANIZATION")
    private String volunteer_type;

    @Schema(description = "봉사 조직 이름 (일반 유저(USER)이거나 개인 봉사자(SINGLE)일 경우 null이 반환됩니다.)",
            example = "1365 자원봉사")
    private String organization_name;

    public UserInfoResponse(User user, String volunteer_type, String organization_name) {
        this.user_id = user.getUserId();
        this.id = user.getId();
        this.name = user.getName();
        this.tel = user.getTel();
        this.email = user.getEmail();
        this.volunteer_type = volunteer_type;
        this.organization_name = organization_name;
    }
}
