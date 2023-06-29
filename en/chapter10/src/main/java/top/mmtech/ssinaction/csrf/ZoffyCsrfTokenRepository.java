package top.mmtech.ssinaction.csrf;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoffyCsrfTokenRepository extends JpaRepository<ZoffyCsrfToken,Integer>{

    Optional<ZoffyCsrfToken> findByTokenHolder(String tokenHolder);

}
