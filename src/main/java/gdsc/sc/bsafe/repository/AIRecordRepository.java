package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.User;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AIRecordRepository  extends JpaRepository<AIRecord, Long> {
    @Override
    Optional<AIRecord> findById(Long recordId);

    @Override
    void deleteById(Long recordId);

    Slice<AIRecord> getAIRecordsByUser(User user);
}
