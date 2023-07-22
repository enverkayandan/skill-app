package prodyna.skillApp;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import prodyna.skillApp.entity.Skill;
import prodyna.skillApp.entity.User;
import prodyna.skillApp.service.User.UserService;

import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SkillControllerRemoteTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    private User testUser;

    private String vanillaPassword;

    private Skill testSkill;

    private HttpHeaders headers;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/skills";
    }

    private HttpHeaders createHeaders(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(user.getUsername(), vanillaPassword);
        return headers;
    }

    private User createTestUser() {
        User user = new User();

        user.setUsername(UUID.randomUUID().toString());

        vanillaPassword = UUID.randomUUID().toString().substring(0,6);
        user.setPassword(vanillaPassword);

        userService.createUser(user);

        return user;
    }

    private Skill createTestSkill() {
        Skill skill = new Skill();
        skill.setName(UUID.randomUUID().toString());
        return skill;
    }

    @BeforeAll
    public void setup() {
        testUser = createTestUser();
        headers = createHeaders(testUser);
        testSkill = createTestSkill();
    }

    @Test
    @Order(1)
    public void testGetAllSkills() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl(), HttpMethod.GET, httpEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(2)
    public void testCreateSkill() {
        HttpEntity<Skill> httpEntity = new HttpEntity<>(testSkill, headers);
        ResponseEntity<Skill> response = restTemplate.exchange(getRootUrl(), HttpMethod.POST, httpEntity, Skill.class);
        testSkill.setId(Objects.requireNonNull(response.getBody()).getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(3)
    public void testGetSkill() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Skill> response = restTemplate
                .exchange(
                        getRootUrl() + "/" + testSkill.getId(),
                        HttpMethod.GET,
                        httpEntity,
                        Skill.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testSkill).usingRecursiveComparison().isEqualTo(response.getBody());
    }

    @Test
    @Order(4)
    public void testUpdateSkill() {
        testSkill.setName(UUID.randomUUID().toString());
        HttpEntity<Skill> httpEntity = new HttpEntity<>(testSkill, headers);
        ResponseEntity<Skill> response = restTemplate.exchange(getRootUrl() + "/" + testSkill.getId(), HttpMethod.PUT, httpEntity, Skill.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(testSkill);
    }

    @Test
    @Order(5)
    public void testDeleteSkill() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/" + testSkill.getId(), HttpMethod.DELETE, httpEntity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @AfterAll
    public void cleanup() {
        userService.deleteUser(testUser);
    }
}
