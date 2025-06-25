package coder.himakara.Set_my_goals.dto;


import coder.himakara.Set_my_goals.entity.ReviewCycle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceReviewDto {
    private Long performanceReviewId;
    private ReviewCycle reviewCycle;
    private Integer employee;
    private Integer reviewer;
    private Integer overallRating;
    private String comments;
    private LocalDate reviewDate;
}
