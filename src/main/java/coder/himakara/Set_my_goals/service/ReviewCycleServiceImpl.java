package coder.himakara.Set_my_goals.service;

import coder.himakara.Set_my_goals.dto.ReviewCycleDto;
import coder.himakara.Set_my_goals.entity.ReviewCycle;
import coder.himakara.Set_my_goals.mapper.ReviewCycleMapper;
import coder.himakara.Set_my_goals.repository.ReviewCycleRepo;
import coder.himakara.Set_my_goals.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewCycleServiceImpl implements ReviewCycleService{
    private final ReviewCycleRepo reviewCycleRepo;
    private final ReviewCycleMapper reviewCycleMapper;

    public ReviewCycleServiceImpl(ReviewCycleRepo reviewCycleRepo, ReviewCycleMapper reviewCycleMapper) {
        this.reviewCycleRepo = reviewCycleRepo;
        this.reviewCycleMapper = reviewCycleMapper;
    }

    @Override
    public List<ReviewCycleDto> getAll() {
        List <ReviewCycle> reviewCycles = reviewCycleRepo.findAll();
        if(!reviewCycles.isEmpty()) {
                return reviewCycleMapper.toDTOsList(reviewCycles);
        }else{
                throw new NotFoundException("There are no review cycles available.");
        }
    }
    @Override
    public ReviewCycleDto createCycle(ReviewCycleDto reviewCycleDto) {
        ReviewCycle savedReviewCycle = reviewCycleRepo.save(reviewCycleMapper.toEntity(reviewCycleDto));
        return reviewCycleMapper.toDTO(savedReviewCycle);
    }
}
