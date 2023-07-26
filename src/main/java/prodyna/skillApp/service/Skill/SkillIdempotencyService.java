package prodyna.skillApp.service.Skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SkillIdempotencyService {

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public SkillIdempotencyService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void addToSet(String key) {
        stringRedisTemplate.opsForSet().add(key, "true");
    }

    public boolean isMember(String key) {
        return stringRedisTemplate.opsForSet().isMember(key, "true");
    }
}
