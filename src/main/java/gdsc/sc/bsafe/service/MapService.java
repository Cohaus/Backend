package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.enums.RepairStatus;
import gdsc.sc.bsafe.domain.mapping.Repair;
import gdsc.sc.bsafe.repository.RepairRepository;
import gdsc.sc.bsafe.repository.projection.CountRepair;
import gdsc.sc.bsafe.web.dto.common.SliceResponse;
import gdsc.sc.bsafe.web.dto.response.RequestRepairListResponse;
import gdsc.sc.bsafe.web.dto.response.RequestRepairResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MapService {

    private final RepairRepository repairRepository;

    public RequestRepairListResponse getRepairListByDistrict(User user, Long legalDistrictId) {
        Slice<Repair> requestRepairs = repairRepository
                .findAllByStatusAndLegalDistrictDistrictIdOrderByCreatedAt(RepairStatus.REQUEST, legalDistrictId);
        SliceResponse<RequestRepairResponse> requestRepairsList = new SliceResponse<>(
                requestRepairs.map(repair -> {
                    String category = repair.getRecord().getCategory().getDescription();
                    return new RequestRepairResponse(repair, category);
                }));

        RequestRepairListResponse response = new RequestRepairListResponse();
        response.setRequest_repairs(requestRepairsList);

        return response;
    }

    public List<CountRepair> getRepairCount(User user) {
        return repairRepository.countRepairGroupByDistrict();
    }

}
