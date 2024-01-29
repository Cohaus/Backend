package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.District;
import gdsc.sc.bsafe.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;

    @Transactional
    public District findDistrictByGuAndDong(String[] district) {
        String sido = district[1];
        String gu = (district.length == 5) ? district[2] + " " + district[3] : district[2];
        String dong = district[(district.length == 5) ? 4 : 3];

        return districtRepository.findDistrictByGuAndDong(gu, dong)
                .orElseGet(() -> saveDistrict(sido, gu, dong));
    }

    @Transactional
    public District saveDistrict(String sido, String gu, String dong) {
        District district = District.builder()
                .sido(sido)
                .gu(gu)
                .dong(dong)
                .build();

        return districtRepository.save(district);
    }

}
