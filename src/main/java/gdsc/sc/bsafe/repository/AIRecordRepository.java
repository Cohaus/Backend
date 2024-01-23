package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.AIRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AIRecordRepository  extends JpaRepository<AIRecord, Long> {
    @Override
    Optional<AIRecord> findById(Long recordId);
}
