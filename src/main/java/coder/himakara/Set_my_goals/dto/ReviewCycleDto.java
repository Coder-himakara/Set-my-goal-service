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
public class ReviewCycleDto {
    private Long reviewCycleId;
    private String cycleName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer createdBy;
}
