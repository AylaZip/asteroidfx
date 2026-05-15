package dk.sdu.cbse.asteroidsfx.scoring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/score")
public class ScoringController {
    private int score = 0;

    @GetMapping
    public int getScore() {
        return score;
    }

    @PostMapping("/add")
    public int addScore(@RequestParam("points") int points) {
        score += points;
        return score;
    }

    @PostMapping("/reset")
    public void resetScore() {
        score = 0;
    }
}
