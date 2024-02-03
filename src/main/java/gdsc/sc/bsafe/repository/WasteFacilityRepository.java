package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.District;
import gdsc.sc.bsafe.domain.WasteFacility;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface WasteFacilityRepository extends JpaRepository<WasteFacility, Long> {

    Slice<WasteFacility> findWasteFacilitiesBySidoAndGu(String sido, String gu);

}
