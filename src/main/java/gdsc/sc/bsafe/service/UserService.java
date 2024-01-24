package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
