package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByNickname(String nickname);
    Optional<User> findByEmail(String email);
}
