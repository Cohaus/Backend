package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.AuthToken;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.web.dto.request.LoginRequest;
import gdsc.sc.bsafe.web.dto.response.LoginResponse;
import gdsc.sc.bsafe.global.jwt.AuthTokenGenerator;
import gdsc.sc.bsafe.global.security.Password;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.repository.UserRepository;
import gdsc.sc.bsafe.web.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static gdsc.sc.bsafe.global.security.Password.ENCODER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthTokenGenerator authTokenGenerator;
    private final AuthTokenService authTokenService;

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        userRepository.save(signUpRequest.toUser());
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findById(loginRequest.id())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        comparePassword(loginRequest.password(), user.getPassword());

        AuthToken generatedToken = authTokenGenerator.generate(user.getUserId(), user.getId());

        return new LoginResponse(
                user.getUserId(),
                generatedToken.getAccessToken(),
                generatedToken.getRefreshToken()
        );
    }

    @Transactional
    public void logout(Long userId) {
        authTokenService.deleteToken(userId);
    }

    private void comparePassword(String password, Password savedPassword) {
        if(!savedPassword.isSamePassword(password, ENCODER)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }
}