package coder.himakara.Set_my_goals.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalDto {
    private int goalId;
    private String title;
    private String description;
    private String status;
    private LocalDate dueDate;
    private int employee;
}
