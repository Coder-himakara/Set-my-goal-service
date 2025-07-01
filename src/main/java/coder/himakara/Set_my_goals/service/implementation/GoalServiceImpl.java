package coder.himakara.Set_my_goals.service.implementation;

import coder.himakara.Set_my_goals.dto.GoalDto;
import coder.himakara.Set_my_goals.dto.ReviewCycleDto;
import coder.himakara.Set_my_goals.dto.response.GoalResponseDto;
import coder.himakara.Set_my_goals.entity.Goal;
import coder.himakara.Set_my_goals.enumeration.GoalStatus;
import coder.himakara.Set_my_goals.exception.IllegalArgumentException;
import coder.himakara.Set_my_goals.mapper.GoalMapper;
import coder.himakara.Set_my_goals.repository.GoalRepo;
import coder.himakara.Set_my_goals.service.GoalService;
import coder.himakara.Set_my_goals.service.ReviewCycleService;
import coder.himakara.Set_my_goals.exception.ModificationNotAllowedException;
import coder.himakara.Set_my_goals.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

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

    /**
     * Retrieves a goal by its unique identifier.
     * This method fetches the goal along with its associated comments.
     *
     * @return A {@link GoalResponseDto} containing the goal data and its comments
     */
    @Override
    public GoalResponseDto getGoalById(Long id) {
        Goal goal = goalRepo.findByIdWithComments(id)
                .orElseThrow(() -> new NotFoundException("Goal with ID " + id + " not found."));
        return goalMapper.toResponseDto(goal);
    }

    @Override
    public List<GoalResponseDto> getAllGoals() {
        List<Goal> goals = goalRepo.findAllWithoutComments();
        if (!goals.isEmpty()) {
            return goalMapper.toResponseDtoListWithoutComments(goals);
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

        if (goalDto.getDueDate() != null && goalDto.getDueDate().isAfter(ongoingCycle.getEndDate())) {
            throw new IllegalArgumentException("Goal due date must be before the end of the current review cycle: " +
                    ongoingCycle.getEndDate());
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

    /**
     * Validates if a goal can be modified (update or delete)
     * @throws NotFoundException If no goal exists with the given ID
     * @throws ModificationNotAllowedException If the goal's status is not PENDING
     * @throws ModificationNotAllowedException If the goal was created more than 7 days ago
     */
    private Goal validateGoalForModification(Long id) {
        Goal goal = goalRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Goal with ID " + id + " not found."));

        if (!GoalStatus.PENDING.equals(goal.getStatus())) {
            throw new ModificationNotAllowedException("Cannot modify goal. Only goals with PENDING status can be modified.");
        }
        LocalDate createdDate = goal.getCreatedDate();
        LocalDate currentDate = LocalDate.now();
        long daysDifference = ChronoUnit.DAYS.between(createdDate, currentDate);
        long validityPeriod = 7; // days

        if (daysDifference > validityPeriod) {
            throw new ModificationNotAllowedException("Cannot modify goal. Goals can only be modified within 7 days of creation.");
        }
        return goal;
    }

    @Override
    public void deleteGoal(Long id) {
        Goal goal = validateGoalForModification(id);
        goalRepo.delete(goal);
    }

    /**
     * Updates specific fields of a goal identified by the given ID.
     * Only title, description, and dueDate fields can be modified.
     * @param fields A map containing field names as keys and new values
     */
    @Override
    public GoalResponseDto updateGoal(Long id, Map<String, Object> fields) {
        Goal existingGoal = validateGoalForModification(id);
        List<String> allowedFields = List.of("title", "description", "dueDate");

        fields.forEach((key, value) -> {
            if (!allowedFields.contains(key)) {
                return;
            }
            Field field = ReflectionUtils.findField(Goal.class, key);
            if (field != null) {
                field.setAccessible(true);
                try {
                    // Handle date conversion if needed
                    Object convertedValue = value;
                    if (key.equals("dueDate") && value instanceof String) {
                        convertedValue = LocalDate.parse((String) value);
                    }
                    ReflectionUtils.setField(field, existingGoal, convertedValue);
                } catch (IllegalArgumentException ex) {
                    throw new IllegalArgumentException("Invalid value for field: " + key);
                }
            }
        });
        return goalMapper.toResponseDto(goalRepo.save(existingGoal));
    }

    @Override
    public GoalResponseDto updateGoalStatusToProgress(Long id) {
        Goal goal = goalRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Goal not found with ID: " + id));

        if (goal.getStatus() != GoalStatus.PENDING) {
            throw new IllegalStateException("Goal must be in PENDING status to move to IN_PROGRESS");
        }

        goal.setStatus(GoalStatus.IN_PROGRESS);
        Goal updatedGoal = goalRepo.save(goal);
        return goalMapper.toResponseDto(updatedGoal);
    }
}
