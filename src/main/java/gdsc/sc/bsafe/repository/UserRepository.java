package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(String id);
}
