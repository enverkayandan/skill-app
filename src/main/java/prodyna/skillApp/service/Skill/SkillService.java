package prodyna.skillApp.service.Skill;

import prodyna.skillApp.model.entity.Skill;

import java.util.List;

public interface SkillService {
    Skill getSkillById(Long id);

    Skill createSkill(Skill skill);

    Skill updateSkill(Long id, Skill skill);

    void deleteSkill(Long id);

    List<Skill> getAllSkills();

    Skill getSkillByName(String name);
}