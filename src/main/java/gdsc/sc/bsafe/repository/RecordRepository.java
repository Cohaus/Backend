package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    @Override
    Optional<Record> findById(Long recordId);

    List<Record> findAllByUser(User user);

    void deleteAllByUser(User user);

}

