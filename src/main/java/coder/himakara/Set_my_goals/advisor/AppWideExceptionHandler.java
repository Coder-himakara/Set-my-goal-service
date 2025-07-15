package coder.himakara.Set_my_goals.advisor;

import coder.himakara.Set_my_goals.exception.Goal_IllegalArgumentException;
import coder.himakara.Set_my_goals.exception.OngoingCycleException;
import coder.himakara.Set_my_goals.util.ErrorResponse;
import coder.himakara.Set_my_goals.exception.ModificationNotAllowedException;
import coder.himakara.Set_my_goals.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class AppWideExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponse(500, "An unexpected error occurred", ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpServletRequest httpServletRequest) {
        ErrorResponse error = new ErrorResponse(
                404,
                "Page Not Found",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(
                new ErrorResponse(404, "Resource Not Found", e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(ModificationNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleModificationNotAllowed(ModificationNotAllowedException e) {
        return new ResponseEntity<>
                (
                new ErrorResponse(403, "Modification Not Allowed", e.getMessage()),
                HttpStatus.BAD_REQUEST
                );
    }
    @ExceptionHandler(Goal_IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Goal_IllegalArgumentException e) {
        return new ResponseEntity<>(
                new ErrorResponse(400, "Bad Request", e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(OngoingCycleException.class)
    public ResponseEntity<ErrorResponse> handleOngoingCycleException(OngoingCycleException e) {
        return new ResponseEntity<>(
                new ErrorResponse(409, "Bad Request", e.getMessage()),
                HttpStatus.CONFLICT
        );
    }
}
