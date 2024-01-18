package gdsc.sc.bsafe.global.auth;

import gdsc.sc.bsafe.domain.AuthToken;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.dto.response.AccessTokenResponse;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.ErrorCode;
import gdsc.sc.bsafe.global.jwt.JwtTokenProvider;
import gdsc.sc.bsafe.repository.AuthTokenRepository;
import gdsc.sc.bsafe.repository.UserRepository;
import gdsc.sc.bsafe.service.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokenGenerator {

    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTokenService authTokenService;
    private final UserRepository userRepository;
    private final AuthTokenRepository authTokenRepository;

    public AuthToken generate(Long userId, String id) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = userId.toString();
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        String accessToken = jwtTokenProvider.generate(subject, id, user.getAuthority(), accessTokenExpiredAt);
        String refreshToken = jwtTokenProvider.generate(subject, id, user.getAuthority(), refreshTokenExpiredAt);

        AuthToken authToken = AuthToken.of(
                userId,
                accessToken,
                refreshToken,
                BEARER_TYPE,
                ACCESS_TOKEN_EXPIRE_TIME / 1000L
        );

        authTokenRepository.save(authToken);

        return authToken;
    }

    public Long extractUserId(String token) {
        return Long.valueOf(jwtTokenProvider.extractSubject(token));
    }

    public AccessTokenResponse accessTokenByRefreshToken(String refreshToken) {
        Long userId = extractUserId(refreshToken);
        String token = authTokenService.getToken(userId);

        if (!token.equals(refreshToken)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REFRESH_TOKEN);
        }

        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        String newAccessToken = jwtTokenProvider.generate(userId.toString(), user.getId(), user.getAuthority(), accessTokenExpiredAt);

        return AccessTokenResponse.of(newAccessToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

    public void deleteRefreshToken(String accessToken) {
        Long userId = extractUserId(accessToken);
        String token = authTokenService.getToken(userId);

        if (token == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }

        authTokenService.deleteToken(userId);
    }
}