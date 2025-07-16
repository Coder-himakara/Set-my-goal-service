package coder.himakara.Set_my_goals.service;

import coder.himakara.Set_my_goals.dto.ReviewCycleDto;
import coder.himakara.Set_my_goals.dto.response.ReviewCycleResponse;

import java.util.List;

public interface ReviewCycleService {
    List<ReviewCycleResponse> getAll();
    ReviewCycleResponse getById(Long id);
    ReviewCycleResponse createCycle(ReviewCycleDto reviewCycleDto);
    ReviewCycleResponse updateCycle(Long id, ReviewCycleDto reviewCycleDto);
    ReviewCycleDto ongoingCycle();
}
