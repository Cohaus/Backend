package gdsc.sc.bsafe.web.dto.response;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.enums.Authority;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    private Long user_id;

    private String id;

    private String name;

    private String tel;

    private String email;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public UserInfoResponse(User user) {
        this.user_id = user.getUserId();
        this.id = user.getId();
        this.name = user.getName();
        this.tel = user.getTel();
        this.tel = user.getEmail();
        this.authority = user.getAuthority();
    }
}
