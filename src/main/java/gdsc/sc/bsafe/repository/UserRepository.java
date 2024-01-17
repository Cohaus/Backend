package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
