package prodyna.skillApp.service.User;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import prodyna.skillApp.model.entity.Skill;
import prodyna.skillApp.model.entity.User;
import prodyna.skillApp.repository.UserRepository;
import prodyna.skillApp.service.Skill.SkillService;

@Service
public class UserService implements UserDetailsService {

    private final SkillService skillService;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository, SkillService skillService) {
        this.userRepository = userRepository;
        this.skillService = skillService;
    }

    public User createUser(User user) {
        if(userRepository.existsById(user.getUsername())) {
            throw new EntityExistsException("User already exists with username " + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username " + username));
    }

    public User addSkill(String username, String skillName) {
        User user = (User) loadUserByUsername(username);
        Skill skill = skillService.getSkillByName(skillName);

        user.addSkill(skill);
        return user;
    }

    public void deleteUser(User user) {
        if(userRepository.existsById(user.getUsername())) {
            throw new EntityNotFoundException("Cannot delete, User not found with username " + user.getUsername());
        }
        userRepository.delete(user);
    }
}