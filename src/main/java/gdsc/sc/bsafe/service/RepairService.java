package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.District;
import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.enums.RecordType;
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
                .date(repairRequest.getDate())
                .address(repairRequest.getAddress())
                .district(extractDistrict)
                .placeId(repairRequest.getPlace_id())
                .status(RepairStatus.REQUEST)
                .legalDistrict(legalDistrict)
                .build();

        return repairRepository.save(repair);
    }

    @Transactional
    public void updateRepairStatus(Long repairId, RepairStatus status) {
        Repair repair = findByRepairId(repairId);
        repair.updateRepairStatus(status);
    }

    public RepairRecordResponse getRepairRecord(Repair repair){
        String district = repair.getDistrict();
        Record record = repair.getRecord();
        RecordType type ;
        Integer grade ;
        if (record instanceof AIRecord){
            type = RecordType.AI;
            grade = ((AIRecord) record).getGrade();
        }
        else {
            type = RecordType.BASIC;
            grade = null;
        }
        RepairRecordResponse repairRecordResponse = new RepairRecordResponse(repair.getRecord(), district, grade, type);
        return repairRecordResponse;
    }

    public RepairInfoResponse getRepairInfo(Repair repair, User currentUser){
        User user = repair.getRecord().getUser();
        User volunteer = repair.getVolunteer();
        if(currentUser != user | currentUser != volunteer){
            throw new CustomException(ErrorCode.INVALID_PERMISSION);
        }
        String category = repair.getRecord().getCategory();
        Long volunteerId = null;
        String volunteerName = null;
        String volunteerTel = null;
        if(volunteer != null){
            volunteerId = volunteer.getUserId();
            volunteerName = volunteer.getName();
            volunteerTel = volunteer.getTel();
        }
        String address;
        if(repair.getStatus() == RepairStatus.REQUEST){
            address = repair.getDistrict();}
        else {
            address = repair.getDistrict() + ' ' + repair.getAddress();}
        return new RepairInfoResponse(repair,category,user,volunteerId,volunteerName,volunteerTel,address);
    }

}
