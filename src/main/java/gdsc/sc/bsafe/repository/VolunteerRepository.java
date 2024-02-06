package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.mapping.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    Optional<Volunteer> findByUser(User user);

    void deleteAllByUser(User user);
}
