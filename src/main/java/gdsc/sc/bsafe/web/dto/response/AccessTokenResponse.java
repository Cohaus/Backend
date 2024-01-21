package gdsc.sc.bsafe.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponse {

    private String accessToken;
    private String grantType;
    private Long expiresIn;

    public static AccessTokenResponse of(String accessToken, String grantType, Long expiresIn) {
        return new AccessTokenResponse(accessToken, grantType, expiresIn);
    }
}