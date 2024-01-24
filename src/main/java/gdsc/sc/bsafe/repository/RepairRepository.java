package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.mapping.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {

    @Override
    Optional<Repair> findById(Long repairId);
}
