package coder.himakara.Set_my_goals.service;

import coder.himakara.Set_my_goals.dto.GoalDto;

import java.util.List;

public interface GoalService {
    GoalDto getGoalById(Long id);
    List<GoalDto> getAllGoals();
    GoalDto createGoal(GoalDto goalDto);
}
