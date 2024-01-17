package gdsc.sc.bsafe.global.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private  LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String detail;

    public static ResponseEntity<ErrorResponse> toResponseEntity(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .status(errorCode.getHttpStatus().value()) // httpStatus code
                                .error(errorCode.getHttpStatus().name()) // httpStatus name
                                .code(errorCode.name()) // errorCode name
                                .detail(errorCode.getDetail()) // errorCode details
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
                                .code(fieldError.getCode()) // errorCode name
                                .detail(fieldError.getDefaultMessage()) // errorCode details
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

    public void setDateNull(){ this.timestamp = null;}
}
