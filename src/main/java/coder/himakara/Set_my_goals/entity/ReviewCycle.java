package coder.himakara.Set_my_goals.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="review_cycle")
public class ReviewCycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewCycleId;

    private String cycleName;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "reviewCycle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Goal> goals;

    @OneToMany(mappedBy = "reviewCycle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set <PerformanceReview> performanceReviews;
}
