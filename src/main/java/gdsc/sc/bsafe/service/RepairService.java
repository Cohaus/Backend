package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.District;
import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.enums.RecordType;
import gdsc.sc.bsafe.domain.enums.RepairCategory;
import gdsc.sc.bsafe.domain.enums.RepairStatus;
import gdsc.sc.bsafe.domain.mapping.Repair;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.repository.RepairRepository;
import gdsc.sc.bsafe.web.dto.request.RepairRequest;
import gdsc.sc.bsafe.web.dto.response.RepairInfoResponse;
import gdsc.sc.bsafe.web.dto.response.RepairRecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RepairService {

    private final RepairRepository repairRepository;
    private final DistrictService districtService;

    public Repair findByRepairId(Long repairId) {
        return repairRepository.findById(repairId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REPAIR));
    }

    @Transactional
    public Repair createRepair(Record record, RepairRequest repairRequest) {
        repairRepository.findByRecord(record).ifPresent(repair-> {
            throw new CustomException(ErrorCode.DUPLICATED_REPAIR);
        });

        String[] district = repairRequest.getDistrict().split(" ");
        District legalDistrict = districtService.findDistrictByGuAndDong(district);
        String extractDistrict = String.join(" ", Arrays.copyOfRange(district, 1, district.length));

        Repair repair = Repair.builder()
                .record(record)
                .visitDate(repairRequest.getVisit_date())
                .address(repairRequest.getAddress())
                .district(extractDistrict)
                .placeId(repairRequest.getPlace_id())
                .status(RepairStatus.REQUEST)
                .legalDistrict(legalDistrict)
                .build();

        return repairRepository.save(repair);
    }

    @Transactional
    public void updateRequestRepairStatus(Long repairId, RepairStatus status, LocalDate proceedDate) {
        Repair repair = findByRepairId(repairId);
        repair.updateRepairStatus(status);
        repair.updateProceedDate(proceedDate);
    }

    @Transactional
    public void updateCompleteRepairStatus(Long repairId, RepairStatus status, LocalDate completeDate) {
        Repair repair = findByRepairId(repairId);
        repair.updateRepairStatus(status);
        repair.updateCompleteDate(completeDate);
    }

    public RepairRecordResponse getRepairRecord(Repair repair, User user){
        String district = repair.getDistrict();
        Record record = repair.getRecord();
        User writer = record.getUser();
        RepairStatus status ;
        RecordType type ;
        String grade ;

        if (!user.equals(writer)) {
            status = repair.getStatus() ;
        }
        else status = null;

        if (record instanceof AIRecord){
            type = RecordType.AI;
            grade = ((AIRecord) record).getGrade().getDescription();
        }
        else {
            type = RecordType.BASIC;
            grade = null;
        }
        return new RepairRecordResponse(repair.getRecord(), status,district, grade, type);
    }

    public RepairInfoResponse getRepairInfo(Repair repair, User currentUser){
        User user = repair.getRecord().getUser();
        User volunteer = repair.getVolunteer();
        RepairCategory repairCategory = repair.getRecord().getCategory();
        String category = (repairCategory==null)? null : repairCategory.getDescription();
        Long volunteerId = null;
        String userName = null;
        String userTel = null;
        String volunteerName = null;
        String volunteerTel = null;
        String address = removeCountryPrefix(repair.getDistrict());

        // 신청 상태인 경우
        if (repair.getStatus().equals(RepairStatus.REQUEST)){
            if (currentUser == user){
                address += ' ' + repair.getAddress();
                userName = user.getName();
                userTel = user.getTel();
            }
        }
        // 진행 및 완료 상태인 경우
        else {
            if(currentUser.equals(user) || currentUser.equals(volunteer)) {
                userName = user.getName();
                userTel = user.getTel();

                volunteerId = volunteer.getUserId();
                volunteerName = volunteer.getName();
                volunteerTel = volunteer.getTel();

                address += ' ' + repair.getAddress();
            }
            else throw new CustomException(ErrorCode.INVALID_PERMISSION);
        }
        return new RepairInfoResponse(repair,category,user.getUserId(),userName,userTel,
                volunteerId,volunteerName,volunteerTel,address);
    }

    private String removeCountryPrefix(String address) {
        if (address.startsWith("대한민국 ")) {
            return address.substring(6);
        }
        return address;
    }

}
