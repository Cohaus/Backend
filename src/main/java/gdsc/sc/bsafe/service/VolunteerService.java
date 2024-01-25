package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.Organization;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.enums.Authority;
import gdsc.sc.bsafe.domain.enums.RepairStatus;
import gdsc.sc.bsafe.domain.enums.VolunteerType;
import gdsc.sc.bsafe.domain.mapping.Repair;
import gdsc.sc.bsafe.domain.mapping.Volunteer;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.repository.VolunteerRepository;
import gdsc.sc.bsafe.web.dto.request.VolunteerUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final OrganizationService organizationService;
    private final RepairService repairService;

    @Transactional
    public Long saveVolunteer(User user, VolunteerUserRequest request) {
        Volunteer volunteer = Volunteer.builder()
                .user(user)
                .type(VolunteerType.valueOf(request.getType()))
                .build();

        String organizationName = request.getOrganization_name();
        if (organizationName != null && !organizationName.isEmpty()) {
            Organization organization = organizationService.findOrCreateOrganization(organizationName);
            volunteer.updateOrganization(organization);
        }

        Volunteer savedVolunteer = volunteerRepository.save(volunteer);
        user.updateUserAuthority();

        return savedVolunteer.getUser().getUserId();
    }

    @Transactional
    public Long volunteerRepair(User user, Long repairId) {
        Repair repair = repairService.findRepairByRepairId(repairId);

        updateVolunteer(user, repair);
        repairService.updateRepairStatus(repairId, RepairStatus.PROCEEDING);

        return repair.getRepairId();
    }

    private void updateVolunteer(User user, Repair repair) {
        validateVolunteerUser(user);
        repair.updateVolunteer(user);
    }

    private void validateVolunteerUser(User user) {
        if (!user.getAuthority().equals(Authority.VOLUNTEER)) {
            throw new CustomException(ErrorCode.NOT_VOLUNTEER_USER);
        }
    }
}
