package coder.himakara.Set_my_goals.dto.response;

import java.time.LocalDate;

public record ReviewCycleResponse(
        Long reviewCycleId,
        String cycleName,
        LocalDate startDate,
        LocalDate endDate,
        Integer createdBy) {
}
