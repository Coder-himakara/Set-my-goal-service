package coder.himakara.Set_my_goals.service;

import coder.himakara.Set_my_goals.dto.GoalDto;
import coder.himakara.Set_my_goals.dto.response.GoalResponseDto;

import java.util.List;

public interface GoalService {
    GoalResponseDto getGoalById(Long id);
    List<GoalResponseDto> getAllGoals() ;
    GoalResponseDto createGoal(GoalDto goalDto) ;
    void deleteGoal(Long id);
}
