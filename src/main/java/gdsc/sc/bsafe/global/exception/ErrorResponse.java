package gdsc.sc.bsafe.global.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

@Getter
@Builder
public class ErrorResponse {

    private final int status;
    private final String error;
    private final String detail;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .status(errorCode.getHttpStatus().value()) // httpStatus code
                                .error(errorCode.getHttpStatus().name()) // httpStatus name
                                .detail(errorCode.name()) // errorCode details
                                .message(errorCode.getMessage()) // errorCode message
                                .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(HttpStatus httpStatus, FieldError fieldError) {
        return ResponseEntity
                .status(httpStatus)
                .body(
                        ErrorResponse.builder()
                                .status(httpStatus.value()) // httpStatus code
                                .error(httpStatus.name()) // httpStatus name
                                .detail(fieldError.getCode()) // errorCode details
                                .message(fieldError.getDefaultMessage()) // errorCode message
                                .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(HttpStatus httpStatus) {
        return ResponseEntity
                .status(httpStatus)
                .body(
                        ErrorResponse.builder()
                                .status(httpStatus.value()) // httpStatus code
                                .error(httpStatus.name()) // httpStatus name
                                .build()
                );
    }

    public String convertToJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return  mapper.writeValueAsString(this);
    }
}
