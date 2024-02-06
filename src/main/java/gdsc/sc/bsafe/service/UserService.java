package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.enums.Authority;
import gdsc.sc.bsafe.domain.enums.VolunteerType;
import gdsc.sc.bsafe.domain.mapping.Volunteer;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.repository.*;
import gdsc.sc.bsafe.web.dto.request.UpdateUserInfoRequest;
import gdsc.sc.bsafe.web.dto.response.UpdateUserInfoResponse;
import gdsc.sc.bsafe.web.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VolunteerRepository volunteerRepository;
    private final RepairRepository repairRepository;
    private final RecordRepository recordRepository;
    private final AuthTokenRepository authTokenRepository;

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
    public void withdrawUser(User user) {
        List<Record> records = recordRepository.findAllByUser(user);
        for (Record record : records) {
            repairRepository.deleteAllByRecord(record);
        }
        repairRepository.deleteAllByVolunteer(user);

        recordRepository.deleteAllByUser(user);
        volunteerRepository.deleteAllByUser(user);
        authTokenRepository.deleteByUserId(user.getUserId());

        userRepository.deleteById(user.getUserId());
    }

    private void handleUserUpdate(User user, UpdateUserInfoRequest request) {
        user.updateUserInfo(request.getName(), request.getEmail(), request.getTel(), Authority.USER);
    }

}
