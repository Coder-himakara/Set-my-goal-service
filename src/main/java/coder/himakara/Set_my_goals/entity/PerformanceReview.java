package coder.himakara.Set_my_goals.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="performance_review")
public class PerformanceReview {
    @Id
    private int performanceReviewId;

    private int employeeId;
    private String reviewerComments;
    private String employeeFeedback;
    private int performanceRating;
    private LocalDate reviewDate;

    @ManyToOne
    @JoinColumn(name = "review_cycle_id", nullable = false)
    private ReviewCycle reviewCycle;
}
