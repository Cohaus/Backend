package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.WasteFacility;
import gdsc.sc.bsafe.repository.WasteFacilityRepository;
import gdsc.sc.bsafe.web.dto.common.SliceResponse;
import gdsc.sc.bsafe.web.dto.request.LegalDistrictRequest;
import gdsc.sc.bsafe.web.dto.response.WasteFacilityListResponse;
import gdsc.sc.bsafe.web.dto.response.WasteFacilityResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WasteFacilityService {

    private final WasteFacilityRepository wasteFacilityRepository;

    public WasteFacilityListResponse getNearWasteFacilityInfo(LegalDistrictRequest request){
        String district = request.getSido() + " " + request.getGu();
        Slice<WasteFacility> wasteFacilities = wasteFacilityRepository.findWasteFacilitiesByAddressContaining(district);
        return new WasteFacilityListResponse(new SliceResponse<>(wasteFacilities.map(WasteFacilityResponse::new)));
    }

}
