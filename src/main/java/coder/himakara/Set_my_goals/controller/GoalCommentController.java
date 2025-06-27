package coder.himakara.Set_my_goals.controller;

import coder.himakara.Set_my_goals.dto.GoalCommentDto;
import coder.himakara.Set_my_goals.service.GoalCommentService;
import coder.himakara.Set_my_goals.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/goals")
public class GoalCommentController {
    private final GoalCommentService goalCommentService;

    public GoalCommentController(GoalCommentService goalCommentService) {
        this.goalCommentService = goalCommentService;
    }

    @PostMapping("/{goalId}/comments")
    public ResponseEntity<StandardResponse> addComment(@PathVariable Long goalId,
                                                       @RequestBody GoalCommentDto goalCommentDto) {
        Long commentId = goalCommentService.addCommentToGoal(goalId,goalCommentDto);
        URI location = URI.create(String.format("/api/v1/goals/%d/comments/%d", goalId, commentId));
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",location),
                HttpStatus.OK
        );
    }
}
