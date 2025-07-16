package coder.himakara.Set_my_goals.controller;


import coder.himakara.Set_my_goals.dto.ReviewCycleDto;
import coder.himakara.Set_my_goals.dto.response.ReviewCycleResponse;
import coder.himakara.Set_my_goals.service.ReviewCycleService;
import coder.himakara.Set_my_goals.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/set-goals/api/review-cycles")
public class ReviewCycleController {
    private final ReviewCycleService reviewCycleService;

    public ReviewCycleController(ReviewCycleService reviewCycleService) {
        this.reviewCycleService = reviewCycleService;
    }

    @PostMapping("/add")
    public ResponseEntity<StandardResponse> createCycle(@RequestBody ReviewCycleDto reviewCycleDto) {
        ReviewCycleResponse createdCycle = reviewCycleService.createCycle(reviewCycleDto);
        return new ResponseEntity<>(
                new StandardResponse(201,"Success",createdCycle),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public  ResponseEntity<StandardResponse> getAllCycles() {
        List<ReviewCycleResponse> reviewCycles = reviewCycleService.getAll();
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",reviewCycles),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse> getCycleById(@PathVariable("id") Long id) {
        ReviewCycleResponse selectedCycle = reviewCycleService.getById(id);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",selectedCycle),
                HttpStatus.OK
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse> updateCycle(@PathVariable("id") Long id,
                                                        @RequestBody ReviewCycleDto reviewCycleDto) {
        ReviewCycleResponse updatedCycle = reviewCycleService.updateCycle(id, reviewCycleDto);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",updatedCycle),
                HttpStatus.OK
        );
    }

}
