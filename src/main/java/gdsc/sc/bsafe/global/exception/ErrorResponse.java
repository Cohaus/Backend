package gdsc.sc.bsafe.global.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

@Getter
@Builder
public class ErrorResponse {

    @Schema(description = "HTTP 응답 상태코드 (HttpStatus Code)", example = "403")
    private final int status;

    @Schema(description = "HTTP 응답 상태이름 (HttpStatus Name)", example = "FORBIDDEN")
    private final String error;

    @Schema(description = "Application 응답 상태이름", example = "INVALID_PERMISSION")
    private final String detail;

    @Schema(description = "Application 응답 메세지", example = "접근 권한이 없습니다.")
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
