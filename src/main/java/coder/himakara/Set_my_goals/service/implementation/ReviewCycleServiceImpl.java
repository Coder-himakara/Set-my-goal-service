package coder.himakara.Set_my_goals.service.implementation;

import coder.himakara.Set_my_goals.dto.ReviewCycleDto;
import coder.himakara.Set_my_goals.entity.ReviewCycle;
import coder.himakara.Set_my_goals.mapper.ReviewCycleMapper;
import coder.himakara.Set_my_goals.repository.ReviewCycleRepo;
import coder.himakara.Set_my_goals.service.ReviewCycleService;
import coder.himakara.Set_my_goals.util.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class ReviewCycleServiceImpl implements ReviewCycleService {
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
    public ReviewCycleDto getById(Long id){
        ReviewCycle reviewCycle = reviewCycleRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Review cycle with ID " + id + " not found."));
        return reviewCycleMapper.toDTO(reviewCycle);
    }


    /**
     * Checks if there is an ongoing review cycle
     * @return true if an ongoing cycle exists, false otherwise
     */
    private boolean hasOngoingCycle() {
        List<ReviewCycle> existingCycles = reviewCycleRepo.findAll();
        if (existingCycles.isEmpty()) {
            return false;
        }
        LocalDate currentDate = LocalDate.now();
        return existingCycles.stream()
                .anyMatch(cycle ->
                        (currentDate.isEqual(cycle.getStartDate()) || currentDate.isAfter(cycle.getStartDate())) &&
                                (currentDate.isEqual(cycle.getEndDate()) || currentDate.isBefore(cycle.getEndDate())));
    }

    /**
     * Returns the current ongoing review cycle if one exists
     * @return the ongoing review cycle
     * @throws NotFoundException if no ongoing cycle exists
     */
    @Override
    public ReviewCycleDto ongoingCycle() {
        LocalDate currentDate = LocalDate.now();

        return reviewCycleRepo.findAll().stream()
                .filter(cycle ->
                        (currentDate.isEqual(cycle.getStartDate()) || currentDate.isAfter(cycle.getStartDate())) &&
                                (currentDate.isEqual(cycle.getEndDate()) || currentDate.isBefore(cycle.getEndDate())))
                .max(Comparator.comparing(ReviewCycle::getEndDate))
                .map(reviewCycleMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("No ongoing review cycle found."));
    }

    @Override
    public ReviewCycleDto createCycle(ReviewCycleDto reviewCycleDto) {
        if (hasOngoingCycle()) {
            throw new IllegalStateException("Cannot create a new review cycle while another one is ongoing.");
        }

        ReviewCycle savedReviewCycle = reviewCycleRepo.save(reviewCycleMapper.toEntity(reviewCycleDto));
        return reviewCycleMapper.toDTO(savedReviewCycle);
    }


    @Override
    public ReviewCycleDto updateCycle(Long id, ReviewCycleDto reviewCycleDto) {
        if (!reviewCycleRepo.existsById(id)) {
            throw new NotFoundException("Review cycle with ID " + id + " not found.");
        }
        reviewCycleDto.setReviewCycleId(id);
        ReviewCycle updatedReviewCycle = reviewCycleRepo.save(reviewCycleMapper.toEntity(reviewCycleDto));
        return reviewCycleMapper.toDTO(updatedReviewCycle);
    }

}
