package prodyna.skillApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prodyna.skillApp.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}
