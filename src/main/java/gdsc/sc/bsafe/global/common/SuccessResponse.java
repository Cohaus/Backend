package gdsc.sc.bsafe.global.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class SuccessResponse<T> {

    @Schema(description = "HTTP 응답 상태코드 (HttpStatus Code)", example = "200")
    private int status;

    @Schema(description = "응답 메세지", example = "요청이 성공했습니다.")
    private String message;

    @Schema(description = "응답 데이터", example = "요청에 대한 응답 데이터")
    private T data;

    public static <T> ResponseEntity<SuccessResponse<?>> ok(T data) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessCode.OK, data));
    }

    public static <T> ResponseEntity<SuccessResponse<?>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.of(SuccessCode.CREATED, data));
    }

    public static <T> SuccessResponse<?> of(SuccessCode successCode, T data) {
        return SuccessResponse.builder()
                .status(successCode.getHttpStatus().value())
                .message(successCode.getDetail())
                .data(data)
                .build();
    }
}