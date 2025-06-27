package coder.himakara.Set_my_goals.service;

import coder.himakara.Set_my_goals.dto.ReviewCycleDto;

import java.util.List;

public interface ReviewCycleService {
    List<ReviewCycleDto> getAll();
    ReviewCycleDto createCycle(ReviewCycleDto reviewCycleDto);
    ReviewCycleDto ongoingCycle();
    ReviewCycleDto getById(Long id);
    ReviewCycleDto updateCycle(Long id, ReviewCycleDto reviewCycleDto);
}
