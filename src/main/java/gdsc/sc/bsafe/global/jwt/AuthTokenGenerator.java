package gdsc.sc.bsafe.global.jwt;

import gdsc.sc.bsafe.domain.AuthToken;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.repository.UserRepository;
import gdsc.sc.bsafe.service.AuthTokenService;
import gdsc.sc.bsafe.web.dto.response.AccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokenGenerator {

    private static final String BEARER_TYPE = "Bearer";

    @Value("${application.jwt.access_token_expire}")
    private static long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${application.jwt.refresh_token_expire}")
    private static long REFRESH_TOKEN_EXPIRE_TIME;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTokenService authTokenService;
    private final UserRepository userRepository;

    public AuthToken generate(Long userId, String email) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        String accessToken = jwtTokenProvider.generate(email, userId, user.getAuthority(), accessTokenExpiredAt);
        String refreshToken = jwtTokenProvider.generate(email, userId, user.getAuthority(), refreshTokenExpiredAt);

        AuthToken authToken = AuthToken.of(
                userId,
                accessToken,
                refreshToken,
                BEARER_TYPE,
                ACCESS_TOKEN_EXPIRE_TIME / 1000L
        );

        authTokenService.handleAuthTokenUpsert(userId, authToken);

        return authToken;
    }

    public Long extractUserId(String token) {
        return jwtTokenProvider.getUserId(token);
    }

    public AccessTokenResponse accessTokenByRefreshToken(String refreshToken) {
        Long userId = extractUserId(refreshToken);
        String token = authTokenService.getToken(userId);

        if (!token.equals(refreshToken)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REFRESH_TOKEN);
        }

        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        String newAccessToken = jwtTokenProvider.generate(user.getEmail(), userId, user.getAuthority(), accessTokenExpiredAt);

        return AccessTokenResponse.of(newAccessToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }
}