package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.mapping.HelpRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HelpRecordRepository extends JpaRepository<HelpRecord, Long> {

    @Override
    Optional<HelpRecord> findById(Long helpId);
}
