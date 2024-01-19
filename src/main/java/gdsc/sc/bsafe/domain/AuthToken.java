package gdsc.sc.bsafe.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "token")
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    private String accessToken;

    private String refreshToken;

    private String grantType;

    private Long expiresIn;

    public AuthToken(Long userId, String accessToken, String refreshToken, String grantType, Long expiresIn) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.grantType = grantType;
        this.expiresIn = expiresIn;
    }

    public static AuthToken of(Long userId, String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new AuthToken(userId, accessToken, refreshToken, grantType, expiresIn);
    }

    public void updateAuthToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
