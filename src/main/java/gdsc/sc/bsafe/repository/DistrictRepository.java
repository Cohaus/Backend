package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query(value = "SELECT d.* FROM district d WHERE d.gu = :gu AND d.dong = :dong", nativeQuery = true)
    Optional<District> findDistrictByGuAndDong(@Param("gu") String gu,
                                               @Param("dong") String dong);

}
