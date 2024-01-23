package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordRepository<T extends Record> extends JpaRepository<T, Long> {

    @Override
    Optional<T> findById(Long recordId);


}

