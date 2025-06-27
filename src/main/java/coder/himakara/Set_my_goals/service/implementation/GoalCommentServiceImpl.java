package coder.himakara.Set_my_goals.service.implementation;

import coder.himakara.Set_my_goals.dto.GoalCommentDto;
import coder.himakara.Set_my_goals.entity.Goal;
import coder.himakara.Set_my_goals.entity.GoalComment;
import coder.himakara.Set_my_goals.enumeration.GoalStatus;
import coder.himakara.Set_my_goals.mapper.GoalCommentMapper;
import coder.himakara.Set_my_goals.repository.GoalCommentRepo;
import coder.himakara.Set_my_goals.repository.GoalRepo;
import coder.himakara.Set_my_goals.service.GoalCommentService;
import coder.himakara.Set_my_goals.util.exception.ModificationNotAllowedException;
import coder.himakara.Set_my_goals.util.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
public class GoalCommentServiceImpl implements GoalCommentService {
    private final GoalCommentRepo goalCommentRepo;
    private final GoalRepo goalRepo;
    private final GoalCommentMapper goalCommentMapper;

    public GoalCommentServiceImpl(GoalCommentRepo goalCommentRepo,
                                  GoalRepo goalRepo,GoalCommentMapper goalCommentMapper ) {
        this.goalCommentRepo = goalCommentRepo;
        this.goalRepo = goalRepo;
        this.goalCommentMapper = goalCommentMapper;
    }

    @Override
    @Transactional
    public Long addCommentToGoal(Long goalId, GoalCommentDto commentDto) {
        if (goalId == null) {
            throw new IllegalArgumentException("Goal ID cannot be null");
        }
        if (commentDto == null || commentDto.getCommentText() == null || commentDto.getCommentText().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be null or empty");
        }

        Goal goal = goalRepo.findById(goalId)
                .orElseThrow(() -> new NotFoundException("Goal with ID " + goalId + " not found."));

        if (!GoalStatus.IN_PROGRESS.equals(goal.getStatus())) {
            throw new ModificationNotAllowedException("Goal with ID " + goalId + " is not in progress.");
        }

        GoalComment newComment = goalCommentMapper.toEntity(commentDto);
        newComment.setGoal(goal);
        newComment.setCommentedAt(LocalDate.now());
        GoalComment savedComment = goalCommentRepo.save(newComment);
        return savedComment.getId();
    }
}
