package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.enums.RepairStatus;
import gdsc.sc.bsafe.domain.mapping.Repair;
import gdsc.sc.bsafe.repository.RepairRepository;
import gdsc.sc.bsafe.web.dto.request.RepairRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RepairService {
    private final RepairRepository repairRepository;

    public Repair createRepair(Record record, RepairRequest repairRequest){
        Repair repair = Repair.builder()
                .record(record)
                .date(repairRequest.getDate())
                .address(repairRequest.getAddress())
                .district(repairRequest.getDistrict())
                .placeId(repairRequest.getPlaceId())
                .status(RepairStatus.REQUEST)
                .build();
        Repair savedRepair = repairRepository.save(repair);
        return savedRepair;
    }

}
