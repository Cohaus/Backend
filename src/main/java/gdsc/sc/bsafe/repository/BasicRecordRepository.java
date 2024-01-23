package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.BasicRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasicRecordRepository extends JpaRepository<BasicRecord, Long> {

    @Override
    Optional<BasicRecord> findById(Long recordId);
}
