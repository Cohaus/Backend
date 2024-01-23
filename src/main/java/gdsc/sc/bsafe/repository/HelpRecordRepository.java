package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.mapping.HelpRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HelpRecordRepository extends JpaRepository<HelpRecord, Long> {

    @Override
    Optional<HelpRecord> findById(Long helpId);
}
