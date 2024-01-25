package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.enums.RepairStatus;
import gdsc.sc.bsafe.domain.mapping.Repair;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.repository.RepairRepository;
import gdsc.sc.bsafe.web.dto.request.RepairRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RepairService {

    private final RepairRepository repairRepository;

    public Repair findRepairByRepairId(Long repairId) {
        return repairRepository.findById(repairId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REPAIR));
    }

    @Transactional
    public Repair createRepair(Record record, RepairRequest repairRequest) {
        Repair repair = Repair.builder()
                .record(record)
                .date(repairRequest.getDate())
                .address(repairRequest.getAddress())
                .district(repairRequest.getDistrict())
                .placeId(repairRequest.getPlace_id())
                .status(RepairStatus.REQUEST)
                .build();

        return repairRepository.save(repair);
    }

    @Transactional
    public void updateRepairStatus(Long repairId, RepairStatus status) {
        Repair repair = findRepairByRepairId(repairId);
        repair.updateRepairStatus(status);
    }
}
