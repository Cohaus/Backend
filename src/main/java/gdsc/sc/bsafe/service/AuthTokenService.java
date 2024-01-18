package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.AuthToken;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.ErrorCode;
import gdsc.sc.bsafe.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenService {
    private final AuthTokenRepository authTokenRepository;

    public String getToken(Long userId) {
        AuthToken authToken = authTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TOKEN_DATA));

        return authToken.getRefreshToken();
    }

    public void deleteToken(Long userId) {
        authTokenRepository.deleteByUserId(userId);
    }
}