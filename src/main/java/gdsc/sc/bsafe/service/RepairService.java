package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.enums.RepairStatus;
import gdsc.sc.bsafe.domain.mapping.Repair;
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

    @Transactional
    public Repair createRepair(RepairRequest repairRequest) {

        Repair repair = Repair.builder()
                .record(repairRequest.getRecord())
                .date(repairRequest.getDate())
                .address(repairRequest.getAddress())
                .district(repairRequest.getDistrict())
                .placeId(repairRequest.getPlaceId())
                .status(RepairStatus.REQUEST)
                .build();

        return repairRepository.save(repair);
    }

}
