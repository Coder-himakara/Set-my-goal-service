package coder.himakara.Set_my_goals.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalCommentResponseDto {
    private Long id;
    private Long goal;
    private String commentText;
    private LocalDate commentedAt;
}
