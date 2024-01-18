package gdsc.sc.bsafe.repository;

import gdsc.sc.bsafe.domain.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    void deleteByUserId(Long userId);
    Optional<AuthToken> findByUserId(Long userId);
}