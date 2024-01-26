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

    @Transactional
    public UpdateUserInfoResponse updateUserInfo(User user, UpdateUserInfoRequest request) {
        User requestUser = findById(user.getUserId());
        String userAuthority = request.getUser_authority();
        String volunteerType = null;
        String organizationName = null;

        switch (Authority.valueOf(userAuthority)) {
            case USER:
                requestUser.updateUserInfo(request.getName(), request.getId(), request.getEmail(), request.getTel(), Authority.USER);
                break;
            case VOLUNTEER:
                if (VolunteerType.SINGLE.toString().equals(request.getVolunteer_type())) {
                    requestUser.updateUserInfo(request.getName(), request.getId(), request.getEmail(), request.getTel(), Authority.VOLUNTEER);
                    Optional<Volunteer> volunteer = volunteerRepository.findByUser(requestUser);
                    volunteer.ifPresentOrElse(
                            volunteerUser -> volunteerUser.updateVolunteerType(VolunteerType.SINGLE),
                            () -> volunteerRepository.save(Volunteer.builder().user(requestUser).type(VolunteerType.SINGLE).build())
                    );
                    volunteerType = VolunteerType.SINGLE.toString();
                } else if (VolunteerType.ORGANIZATION.toString().equals(request.getVolunteer_type())) {
                    requestUser.updateUserInfo(request.getName(), request.getId(), request.getEmail(), request.getTel(), Authority.VOLUNTEER);
                    Optional<Volunteer> volunteer = volunteerRepository.findByUser(requestUser);
                    volunteer.ifPresentOrElse(
                            volunteerUser -> volunteerUser.updateVolunteerType(VolunteerType.ORGANIZATION),
                            () -> volunteerRepository.save(Volunteer.builder().user(requestUser).type(VolunteerType.ORGANIZATION).build())
                    );
                    volunteer.ifPresent(vol -> volunteerService.updateVolunteerOrganization(vol, request.getOrganization_name()));
                    volunteerType = VolunteerType.ORGANIZATION.toString();
                    organizationName = request.getOrganization_name();
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid user authority: " + userAuthority);
        }

        return new UpdateUserInfoResponse(
                requestUser.getUserId(),
                requestUser.getName(),
                requestUser.getId(),
                requestUser.getEmail(),
                requestUser.getTel(),
                volunteerType,
                organizationName
        );
    }

    @Transactional
    public void withdrawUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
