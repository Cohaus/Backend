package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.dto.request.LoginRequest;
import gdsc.sc.bsafe.dto.request.SignUpRequest;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.ErrorCode;
import gdsc.sc.bsafe.global.security.Password;
import gdsc.sc.bsafe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static gdsc.sc.bsafe.global.security.Password.ENCODER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        userRepository.save(signUpRequest.toUser());
    }

    @Transactional
    public Long login(LoginRequest loginRequest) {
        User user = userRepository.findById(loginRequest.id())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        comparePassword(loginRequest.password(), user.getPassword());

        return user.getUserId();
    }

    private void comparePassword(String password, Password savedPassword) {
        if(!savedPassword.isSamePassword(password, ENCODER)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }
}