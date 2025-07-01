package coder.himakara.Set_my_goals.dto.response;

import coder.himakara.Set_my_goals.dto.GoalCommentDto;
import coder.himakara.Set_my_goals.enumeration.GoalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalResponseDto {
    private Long goalId;
    private String title;
    private String description;
    private GoalStatus status;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private Integer employee;
    private Long reviewCycle;
    private List<GoalCommentDto> comments;
}
