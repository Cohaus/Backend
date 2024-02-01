package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.enums.Authority;
import gdsc.sc.bsafe.domain.enums.VolunteerType;
import gdsc.sc.bsafe.domain.mapping.Volunteer;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.repository.UserRepository;
import gdsc.sc.bsafe.repository.VolunteerRepository;
import gdsc.sc.bsafe.web.dto.request.UpdateUserInfoRequest;
import gdsc.sc.bsafe.web.dto.response.UpdateUserInfoResponse;
import gdsc.sc.bsafe.web.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VolunteerRepository volunteerRepository;
    private final VolunteerService volunteerService;

    public User findById(Long userId){
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    public UserInfoResponse getUserInfo(User user){
        Optional<Volunteer> volunteer = volunteerRepository.findByUser(user);
        String volunteer_type = null;
        String organization_name = null ;
        if(volunteer.isPresent()){
            volunteer_type = volunteer.get().getType().getDescription();
            if(volunteer.get().getType().equals(VolunteerType.ORGANIZATION)) {
                organization_name = volunteer.get().getOrganization().getName();
            }
        }
        return new UserInfoResponse(user, volunteer_type, organization_name);
    }

    @Transactional
    public UpdateUserInfoResponse updateUserInfo(User user, UpdateUserInfoRequest request) {
        User requestUser = findById(user.getUserId());
        handleUserUpdate(requestUser, request);

        return new UpdateUserInfoResponse(
                requestUser.getUserId(),
                requestUser.getName(),
                requestUser.getId(),
                requestUser.getEmail(),
                requestUser.getTel()
        );
    }

    @Transactional
    public void withdrawUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private void handleUserUpdate(User user, UpdateUserInfoRequest request) {
        user.updateUserInfo(request.getName(), request.getId(), request.getEmail(), request.getTel(), Authority.USER);
    }


}
