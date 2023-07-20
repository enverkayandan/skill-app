package prodyna.skillApp.dto;

import prodyna.skillApp.entity.Skill;

public class SkillDTO {
    private Long id;
    private String name;

    public SkillDTO() {
    }

    public SkillDTO(Skill skill) {
        this.id = skill.getId();
        this.name = skill.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}