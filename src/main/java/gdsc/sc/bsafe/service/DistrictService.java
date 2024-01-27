package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.District;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;

    public District findDistrictByGuAndDong(String[] district) {
        return districtRepository.findDistrictByGuAndDong(district[1], district[2])
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_DISTRICT));
    }
}
