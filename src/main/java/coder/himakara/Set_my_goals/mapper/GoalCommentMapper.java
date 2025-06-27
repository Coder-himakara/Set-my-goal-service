package coder.himakara.Set_my_goals.mapper;

import coder.himakara.Set_my_goals.dto.GoalCommentDto;
import coder.himakara.Set_my_goals.dto.response.GoalCommentResponseDto;
import coder.himakara.Set_my_goals.entity.GoalComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalCommentMapper {
    @Mapping(target = "goal", ignore = true)
    @Mapping(target = "commentedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    GoalComment toEntity(GoalCommentDto goalCommentDto);

    @Mapping(target = "goal", source = "goal.goalId")
    GoalCommentResponseDto toCommentResponseDto(GoalComment goalComment);

    List<GoalCommentResponseDto> toCommentResponseDtoList(List<GoalComment> goalComments);
}
