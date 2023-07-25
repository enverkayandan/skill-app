package prodyna.skillApp.service.Skill;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prodyna.skillApp.model.entity.Skill;
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
        return skillRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public Skill getSkillByName(String name) {
        return skillRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException());
    }

    public Skill createSkill(Skill skill) {
        if(skillRepository.existsSkillByName(skill.getName()).isPresent()) {
            throw new EntityExistsException("Skill already exists with name " + skill.getName());
        }
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, Skill skill) {
        if (!skillRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot update, Skill not found with id " + id);
        }
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot delete, Skill not found with id " + id);
        }
        skillRepository.deleteById(id);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }


}
