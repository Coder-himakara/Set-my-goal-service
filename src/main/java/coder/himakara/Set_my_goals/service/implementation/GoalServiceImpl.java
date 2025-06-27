package coder.himakara.Set_my_goals.service.implementation;

import coder.himakara.Set_my_goals.dto.GoalDto;
import coder.himakara.Set_my_goals.dto.ReviewCycleDto;
import coder.himakara.Set_my_goals.dto.response.GoalResponseDto;
import coder.himakara.Set_my_goals.entity.Goal;
import coder.himakara.Set_my_goals.enumeration.GoalStatus;
import coder.himakara.Set_my_goals.mapper.GoalMapper;
import coder.himakara.Set_my_goals.repository.GoalRepo;
import coder.himakara.Set_my_goals.service.GoalService;
import coder.himakara.Set_my_goals.service.ReviewCycleService;
import coder.himakara.Set_my_goals.util.exception.DeletionNotAllowedException;
import coder.himakara.Set_my_goals.util.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {
    private final GoalRepo goalRepo;
    private final GoalMapper goalMapper;
    private final ReviewCycleService reviewCycleService;

    public GoalServiceImpl(GoalRepo goalRepo, GoalMapper goalMapper,
                           ReviewCycleService reviewCycleService) {
        this.goalRepo = goalRepo;
        this.goalMapper = goalMapper;
        this.reviewCycleService = reviewCycleService;
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
        ReviewCycleDto ongoingCycle = reviewCycleService.ongoingCycle();
        if (ongoingCycle == null) {
            throw new NotFoundException("No ongoing review cycle found. Please wait until a review cycle starts.");
        }

        try {
            Goal goal = goalMapper.toEntity(goalDto);
            goal.setStatus(GoalStatus.PENDING);
            goal.setCreatedDate(LocalDate.now());
            goal.setReviewCycle(ongoingCycle.getReviewCycleId());
            return goalMapper.toResponseDto(goalRepo.save(goal));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create goal: " + e.getMessage(), e);
        }
    }

    public void deleteGoal(Long id) {
        Goal goal = goalRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Goal with ID " + id + " not found."));

        if (!GoalStatus.PENDING.equals(goal.getStatus())) {
            throw new DeletionNotAllowedException("Cannot delete goal. Only goals with PENDING status can be deleted.");
        }
        LocalDate createdDate = goal.getCreatedDate();
        LocalDate currentDate = LocalDate.now();
        long daysDifference = ChronoUnit.DAYS.between(createdDate, currentDate);
        long validityPeriod = 7; // days

        if (daysDifference > validityPeriod) {
            throw new DeletionNotAllowedException("Cannot delete goal. Goals can only be deleted within 7 days of creation.");
        }
        goalRepo.delete(goal);
    }
}
