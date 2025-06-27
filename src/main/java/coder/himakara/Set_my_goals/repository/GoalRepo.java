package coder.himakara.Set_my_goals.repository;

import coder.himakara.Set_my_goals.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface GoalRepo extends JpaRepository<Goal,Long> {
}
