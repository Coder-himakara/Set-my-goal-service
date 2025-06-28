package coder.himakara.Set_my_goals.repository;

import coder.himakara.Set_my_goals.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface GoalRepo extends JpaRepository<Goal,Long> {
    @Query("SELECT g FROM Goal g LEFT JOIN FETCH g.comments WHERE g.goalId = :id")
    Optional<Goal> findByIdWithComments(@Param("id") Long id);
}
