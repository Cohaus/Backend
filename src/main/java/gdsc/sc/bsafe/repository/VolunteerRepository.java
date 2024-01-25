package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.mapping.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
