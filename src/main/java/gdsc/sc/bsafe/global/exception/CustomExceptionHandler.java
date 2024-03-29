package gdsc.sc.bsafe.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ErrorResponse.toResponseEntity(e);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        FieldError fieldError = e.getFieldError();

        if (Objects.isNull(fieldError)) {
            return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST);
        }

        log.info(fieldError.toString());
        return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, fieldError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity handleException(MethodArgumentNotValidException e) {
        return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, e.getFieldError());
    }

}
