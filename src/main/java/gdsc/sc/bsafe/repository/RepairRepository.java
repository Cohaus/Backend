package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.enums.RepairStatus;
import gdsc.sc.bsafe.domain.mapping.Repair;
import gdsc.sc.bsafe.repository.projection.CountRepair;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {

    @Override
    Optional<Repair> findById(Long repairId);

    Optional<Repair> findByRecord(Record record);

    @Override
    void delete(Repair repair);

    Slice<Repair> findAllByRecord_UserOrderByCreatedAtDesc(User user);

    Slice<Repair> getByVolunteerAndStatus(User user, RepairStatus status);

    @Query(value = "SELECT r.* FROM repair r WHERE r.status = 'REQUEST' ORDER BY r.district_id", nativeQuery = true)
    Slice<Repair> findRequestRepairsOrderByDistrict();

    @Query(value =
            "SELECT "+
                    "new gdsc.sc.bsafe.repository.projection.CountRepair(" +
                        "r.legalDistrict.districtId, " +
                        "r.legalDistrict.sido, " +
                        "r.legalDistrict.gu, " +
                        "r.legalDistrict.dong, " +
                        "count(r)" +
                    ") " +
                    "FROM Repair r " +
                    "WHERE r.status = 'REQUEST' " +
                    "GROUP BY r.legalDistrict "
    )
    List<CountRepair> countRepairGroupByDistrict();

}
