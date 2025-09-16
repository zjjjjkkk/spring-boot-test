package top.zjjjjkkk.model;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Slf4j
class TeamTest {
    @Resource
    private Team team;


    @Test
    void testTeam() {
        log.info("team: {}", team);
        assertEquals("Web2班", team.getLeader());
        assertTrue(team.getPhone().matches("^[0-9]{11}$"));
        assertTrue(team.getAge() >= 1 && team.getAge() <=5);
        assertTrue(team.getCreateDate().isBefore(LocalDate.now()));
    }
}
