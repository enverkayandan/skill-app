package prodyna.skillApp.model.dto;

import prodyna.skillApp.model.entity.Skill;
import prodyna.skillApp.model.entity.User;

import java.util.Set;

public class UserDTO {
    String username;

    Set<Skill> skills;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.skills = user.getSkills();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }
}
