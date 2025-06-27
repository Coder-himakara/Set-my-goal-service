package coder.himakara.Set_my_goals.entity;

import coder.himakara.Set_my_goals.enumeration.GoalStatus;
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
@Table(name = "goal")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    private String title;
    private String description;
    private GoalStatus status;
    private LocalDate createdDate;
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "review_cycle_id", nullable = false)
    private ReviewCycle reviewCycle;

    public void setReviewCycle(Long reviewCycleId) {
        if (this.reviewCycle == null) {
            this.reviewCycle = new ReviewCycle();
        }
        this.reviewCycle.setReviewCycleId(reviewCycleId);
    }
}
