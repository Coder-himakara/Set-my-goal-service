package coder.himakara.Set_my_goals.mapper;

import coder.himakara.Set_my_goals.dto.ReviewCycleDto;
import coder.himakara.Set_my_goals.dto.response.ReviewCycleResponse;
import coder.himakara.Set_my_goals.entity.ReviewCycle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewCycleMapper {
    @Mapping(target = "goals" , ignore = true)
    @Mapping(target = "performanceReviews" , ignore = true)
    ReviewCycle toEntity(ReviewCycleDto reviewCycleDto);

    ReviewCycleDto toDTO(ReviewCycle reviewCycle);
    List<ReviewCycleDto>  toDTOsList(List<ReviewCycle> reviewCycles);

    ReviewCycleResponse toResponseDTO(ReviewCycle reviewCycle);
    List<ReviewCycleResponse> toResponseDTOsList(List<ReviewCycle> reviewCycles);
}
