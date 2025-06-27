package coder.himakara.Set_my_goals.service;

import coder.himakara.Set_my_goals.dto.GoalDto;
import coder.himakara.Set_my_goals.dto.response.GoalResponseDto;
import coder.himakara.Set_my_goals.entity.Goal;
import coder.himakara.Set_my_goals.mapper.GoalMapper;
import coder.himakara.Set_my_goals.repository.GoalRepo;
import coder.himakara.Set_my_goals.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {
    private final GoalRepo goalRepo;
    private final GoalMapper goalMapper;

    public GoalServiceImpl(GoalRepo goalRepo, GoalMapper goalMapper) {
        this.goalRepo = goalRepo;
        this.goalMapper = goalMapper;
    }

    @Override
    public GoalResponseDto getGoalById(Long id) {
        Goal goal = goalRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Goal with ID " + id + " not found."));
        return goalMapper.toResponseDto(goal);
    }

    @Override
    public List<GoalResponseDto> getAllGoals() {
        List<Goal> goal = goalRepo.findAll();
        if (!goal.isEmpty()) {
            return goalMapper.toResponseDtoList(goal);
        } else {
            throw new NotFoundException("There are no goals available.");
        }
    }

    @Override
    public GoalResponseDto createGoal(GoalDto goalDto) {
        Goal savedGoal = goalRepo.save(goalMapper.toEntity(goalDto));
        return goalMapper.toResponseDto(savedGoal);
    }
}
