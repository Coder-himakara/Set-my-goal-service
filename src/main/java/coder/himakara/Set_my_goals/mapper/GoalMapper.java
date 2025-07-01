package coder.himakara.Set_my_goals.mapper;

import coder.himakara.Set_my_goals.dto.GoalDto;
import coder.himakara.Set_my_goals.dto.response.GoalResponseDto;
import coder.himakara.Set_my_goals.entity.Employee;
import coder.himakara.Set_my_goals.entity.Goal;
import coder.himakara.Set_my_goals.entity.ReviewCycle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalMapper {
    @Mapping(target = "goalId", ignore = true)
    @Mapping(target = "reviewCycle", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "employee", source = "employee", qualifiedByName = "mapEmployee")
    Goal toEntity(GoalDto goalDto);

    //includes comments
    @Mapping(target = "employee", source = "employee.employeeId")
    @Mapping(target = "reviewCycle", source = "reviewCycle.reviewCycleId")
    GoalResponseDto toResponseDto(Goal goal);

    //explicitly excludes comments
    @Mapping(target = "employee", source = "employee.employeeId")
    @Mapping(target = "reviewCycle", source = "reviewCycle.reviewCycleId")
    @Mapping(target = "comments", ignore = true)
    GoalResponseDto toResponseDtoWithoutComments(Goal goal);

    // Use a default method for list with comments to avoid ambiguity
    default List<GoalResponseDto> toResponseDtoList(List<Goal> goals) {
        return goals.stream()
                .map(this::toResponseDto)
                .toList();
    }

    // Maps a list of goals without comments
    default List<GoalResponseDto> toResponseDtoListWithoutComments(List<Goal> goals) {
        return goals.stream()
                .map(this::toResponseDtoWithoutComments)
                .toList();
    }

    @Named("mapEmployee")
    default Employee mapEmployee(Integer employeeId) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        return employee;
    }

    @Named("mapReviewCycle")
    default ReviewCycle mapReviewCycle(Long cycleId) {
        ReviewCycle reviewCycle = new ReviewCycle();
        reviewCycle.setReviewCycleId(cycleId);
        return reviewCycle;
    }
}