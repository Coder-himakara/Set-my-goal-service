package coder.himakara.Set_my_goals.mapper;

import coder.himakara.Set_my_goals.dto.GoalDto;
import coder.himakara.Set_my_goals.entity.Goal;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalMapper {
    Goal toEntity(GoalDto goalDto);
    GoalDto toDTO(Goal goal);
    List<GoalDto> toDTOList(List<Goal> goals);
}
