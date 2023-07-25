package prodyna.skillApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prodyna.skillApp.model.dto.UserDTO;
import prodyna.skillApp.model.entity.Skill;
import prodyna.skillApp.model.entity.User;
import prodyna.skillApp.service.User.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        return new ResponseEntity<>(new UserDTO(userService.createUser(user)), HttpStatus.CREATED);
    }

    @PutMapping("/{username}/addSkill")
    public ResponseEntity<UserDTO> addSkill(@PathVariable String username, @RequestBody Skill skill) {
        return new ResponseEntity<>(new UserDTO(userService.addSkill(username, skill.getName())), HttpStatus.CREATED);
    }

}