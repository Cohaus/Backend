package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.repository.RepairRepository;
import gdsc.sc.bsafe.repository.projection.CountRepair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MapService {

    private final RepairRepository repairRepository;

    public List<CountRepair> getRepairCount(User user) {
        return repairRepository.countRepairGroupByDistrict();
    }

}
