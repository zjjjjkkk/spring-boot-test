package top.zjjjjkkk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.zjjjjkkk.model.Team;

@RestController
@RequestMapping("/teams")
@Slf4j
public class TeamController {

    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        log.info("创建团队：{}", team);
        return team;
    }
}