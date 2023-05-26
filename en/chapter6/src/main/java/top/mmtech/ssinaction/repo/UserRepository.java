package top.mmtech.ssinaction.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import top.mmtech.ssinaction.model.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findUserByUsername(String username);

}
