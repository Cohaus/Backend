package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.AIRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AIRecordRepository  extends JpaRepository<AIRecord, Long> {
    @Override
    Optional<AIRecord> findById(Long recordId);
}
