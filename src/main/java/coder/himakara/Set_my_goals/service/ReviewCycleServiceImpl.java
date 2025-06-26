package coder.himakara.Set_my_goals.service;

import coder.himakara.Set_my_goals.dto.ReviewCycleDto;
import coder.himakara.Set_my_goals.entity.ReviewCycle;
import coder.himakara.Set_my_goals.mapper.ReviewCycleMapper;
import coder.himakara.Set_my_goals.repository.ReviewCycleRepo;
import coder.himakara.Set_my_goals.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
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
        try {
            ReviewCycleDto ongoingCycle = ongoingCycle();
            throw new IllegalStateException("Cannot create a new review cycle " +
                    "while another one is ongoing. Current cycle ends on " + ongoingCycle.getEndDate());
        } catch (NotFoundException e) {
            ReviewCycle savedReviewCycle = reviewCycleRepo.save(reviewCycleMapper.toEntity(reviewCycleDto));
            return reviewCycleMapper.toDTO(savedReviewCycle);
        }
    }

    @Override
    public ReviewCycleDto ongoingCycle() {
        List<ReviewCycle> existingCycles = reviewCycleRepo.findAll();
        if (!existingCycles.isEmpty()) {
            // Get the most recent cycle
            ReviewCycle latestCycle = existingCycles.stream()
                    .max(Comparator.comparing(ReviewCycle::getEndDate))
                    .orElseThrow(() -> new RuntimeException("Error retrieving latest review cycle"));

            LocalDate currentDate = LocalDate.now();
            // Check if current date is less than or equal to the end date of the most recent cycle
            if (currentDate.isBefore(latestCycle.getEndDate()) || currentDate.isEqual(latestCycle.getEndDate())) {
                return reviewCycleMapper.toDTO(latestCycle);
            }
        }
        throw new NotFoundException("No ongoing review cycle found.");
    }
}
