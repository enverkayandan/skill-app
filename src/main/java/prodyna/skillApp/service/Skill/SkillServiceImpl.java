package prodyna.skillApp.service.Skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prodyna.skillApp.entity.Skill;
import prodyna.skillApp.repository.SkillRepository;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Autowired
    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill getSkillById(Long id) {
        return skillRepository.findById(id).get();
    }

    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, Skill skill) {
        return skillRepository.save(skill);
    }

    public boolean deleteSkill(Long id) {
        Skill skill = getSkillById(id);
        if (skill != null) {
            skillRepository.delete(skill);
            return true;
        }
        return false;
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
}
