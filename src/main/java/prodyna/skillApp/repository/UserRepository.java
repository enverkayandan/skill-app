package prodyna.skillApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prodyna.skillApp.model.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}
