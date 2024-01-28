package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.mapping.Repair;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("select a from AIRecord a " +
            "where a.user = :user and " +
            "not a in :records " +
            "order by a.recordId desc ")
    Slice<AIRecord> queryFindSavedRecords(List<Record> records, User user);

}
