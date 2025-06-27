package coder.himakara.Set_my_goals.controller;

import coder.himakara.Set_my_goals.dto.GoalDto;
import coder.himakara.Set_my_goals.service.GoalService;
import coder.himakara.Set_my_goals.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/goals")
public class GoalController {
 private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/add")
    public ResponseEntity<StandardResponse> createGoal(@RequestBody GoalDto goalDto) {
        GoalDto createdGoal = goalService.createGoal(goalDto);
        return new ResponseEntity<>(
                new StandardResponse(201, "Success", createdGoal),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllGoals() {
        List<GoalDto> goals = goalService.getAllGoals();
        return new ResponseEntity<>(
                new StandardResponse(200, "Success", goals),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse> getGoalById(@PathVariable("id") Long id) {
        GoalDto selectedGoal = goalService.getGoalById(id);
        return new ResponseEntity<>(
                new StandardResponse(200, "Success", selectedGoal),
                HttpStatus.OK
        );
    }

}
