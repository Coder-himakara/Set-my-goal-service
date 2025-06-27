package coder.himakara.Set_my_goals.service;

import coder.himakara.Set_my_goals.dto.GoalDto;
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
    public GoalDto getGoalById(Long id) {
        Goal goal = goalRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Goal with ID " + id + " not found."));
        return goalMapper.toDTO(goal);
    }

    @Override
    public List<GoalDto> getAllGoals() {
        List<Goal> goal = goalRepo.findAll();
        if (!goal.isEmpty()) {
            return goalMapper.toDTOList(goal);
        } else {
            throw new NotFoundException("There are no goals available.");
        }
    }

    @Override
    public GoalDto createGoal(GoalDto goalDto) {
        Goal savedGoal = goalRepo.save(goalMapper.toEntity(goalDto));
        return goalMapper.toDTO(savedGoal);
    }
}
