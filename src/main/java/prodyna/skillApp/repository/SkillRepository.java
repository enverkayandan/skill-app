package prodyna.skillApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prodyna.skillApp.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
}
