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
import gdsc.sc.bsafe.repository.RepairRepository;
import gdsc.sc.bsafe.repository.VolunteerRepository;
import gdsc.sc.bsafe.web.dto.common.SliceResponse;
import gdsc.sc.bsafe.web.dto.request.UpdateUserInfoRequest;
import gdsc.sc.bsafe.web.dto.request.VolunteerUserRequest;
import gdsc.sc.bsafe.web.dto.response.RepairItemResponse;
import gdsc.sc.bsafe.web.dto.response.VolunteerInfoResponse;
import gdsc.sc.bsafe.web.dto.response.VolunteerRepairListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final RepairRepository repairRepository;
    private final OrganizationService organizationService;
    private final RepairService repairService;

    public Optional<Volunteer> findByUser(User user) {
        return volunteerRepository.findByUser(user);
    }

    @Transactional
    public VolunteerInfoResponse saveVolunteer(User user, VolunteerUserRequest request) {
        Volunteer volunteer = createVolunteer(user, VolunteerType.valueOf(request.getType()));

        String organizationName = request.getOrganization_name();
        updateVolunteerOrganization(volunteer, organizationName);
        volunteerRepository.save(volunteer);

        return new VolunteerInfoResponse(request.getType(), request.getOrganization_name());
    }

    @Transactional
    public VolunteerInfoResponse updateVolunteerInfo(Volunteer volunteer, VolunteerUserRequest request) {
        volunteer.updateVolunteerType(VolunteerType.valueOf(request.getType()));
        String organizationName = request.getOrganization_name();
        if (organizationName==null){
            volunteer.updateOrganization(null);
        }
        else {
            updateVolunteerOrganization(volunteer, organizationName);
        }
        volunteerRepository.save(volunteer);

        return new VolunteerInfoResponse(request.getType(), request.getOrganization_name());
    }


    @Transactional
    public Long volunteerRepair(User user, Long repairId, LocalDate proceedDate) {
        Repair repair = repairService.findByRepairId(repairId);
        validateRepairStatus(repair, RepairStatus.REQUEST);

        updateVolunteer(user, repair);
        repairService.updateRequestRepairStatus(repairId, RepairStatus.PROCEEDING, proceedDate);

        return repair.getRepairId();
    }

    @Transactional
    public Long completeRepair(User user, Long repairId, LocalDate completeDate) {
        Repair repair = repairService.findByRepairId(repairId);
        validateRepairStatus(repair, RepairStatus.PROCEEDING);

        validateIsSameVolunteer(user, repair);
        repairService.updateCompleteRepairStatus(repairId, RepairStatus.COMPLETE, completeDate);

        return repair.getRepairId();
    }

    public VolunteerRepairListResponse getVolunteerRepairList(User user) {
        Slice<Repair> proceedingRepairs = repairRepository.getByVolunteerAndStatus(user, RepairStatus.PROCEEDING);
        SliceResponse<RepairItemResponse> proceedingRepairsList = new SliceResponse<>(proceedingRepairs.map(RepairItemResponse::new));

        Slice<Repair> completeRepairs = repairRepository.getByVolunteerAndStatus(user, RepairStatus.COMPLETE);
        SliceResponse<RepairItemResponse> completeRepairsList = new SliceResponse<>(completeRepairs.map(RepairItemResponse::new));

        VolunteerRepairListResponse response= new VolunteerRepairListResponse();
        response.setProceeding_repair(proceedingRepairsList);
        response.setComplete_repair(completeRepairsList);

        return response;
    }

    @Transactional
    public void updateVolunteerOrganization(Volunteer volunteer, String organizationName) {
        if (organizationName != null && !organizationName.isEmpty()) {
            Organization organization = organizationService.findOrCreateOrganization(organizationName);
            volunteer.updateOrganization(organization);
        }
    }

    private Volunteer createVolunteer(User user, VolunteerType type) {
        user.updateUserAuthority(Authority.VOLUNTEER);
        return Volunteer.builder()
                .user(user)
                .type(type)
                .build();
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

    private void validateRepairStatus(Repair repair, RepairStatus status) {
        if (!repair.getStatus().equals(status)) {
            throw new CustomException(ErrorCode.INVALID_REPAIR_STATUS);
        }
    }

    private void validateIsSameVolunteer(User user, Repair repair) {
        if (!repair.getVolunteer().equals(user)) {
            throw new CustomException(ErrorCode.INVALID_PERMISSION);
        }
    }

}
