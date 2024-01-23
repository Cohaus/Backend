package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.BasicRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasicRecordRepository extends JpaRepository<BasicRecord, Long> {

    @Override
    Optional<BasicRecord> findById(Long recordId);
}
