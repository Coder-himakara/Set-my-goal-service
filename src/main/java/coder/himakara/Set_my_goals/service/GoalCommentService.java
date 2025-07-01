package coder.himakara.Set_my_goals.service;

import coder.himakara.Set_my_goals.dto.GoalCommentDto;

public interface GoalCommentService {
    Long addCommentToGoal(Long goalId, GoalCommentDto commentDto);
}
