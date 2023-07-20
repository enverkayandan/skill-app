package prodyna.skillApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import prodyna.skillApp.entity.Skill;
import prodyna.skillApp.service.SkillService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkill(@PathVariable Long id) {
        Skill skill = skillService.getSkillById(id);
        if (skill != null) {
            return ResponseEntity.ok(skill);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill not found");
        }
    }

    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody @Valid Skill skill) {
        Skill createdSkill = skillService.createSkill(skill);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdSkill.getId()).toUri();
        return ResponseEntity.created(location).body(createdSkill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody @Valid Skill updatedSkill) {
        Skill skill = skillService.updateSkill(id, updatedSkill);
        if (skill != null) {
            return ResponseEntity.ok(skill);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}