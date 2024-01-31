package gdsc.sc.bsafe.global.exception.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // User exception
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    NOT_VOLUNTEER_USER(HttpStatus.BAD_REQUEST, "봉사자 권한이 없는 유저입니다."),

    // Auth exception
    UNAUTHORIZED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Refresh Token이 유효하지 않습니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "해당 유저의 Refresh Token을 찾을 수 없습니다."),
    NOT_FOUND_TOKEN_DATA(HttpStatus.NOT_FOUND, "해당 유저의 토큰 정보를 찾을 수 없습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    INVALID_PERMISSION(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    // Record exception
    NOT_FOUND_RECORD(HttpStatus.NOT_FOUND, "해당 기록을 찾을 수 없습니다."),

    // Repair exception
    NOT_FOUND_REPAIR(HttpStatus.NOT_FOUND, "해당 수리 요청을 찾을 수 없습니다."),
    DUPLICATED_REPAIR(HttpStatus.CONFLICT, "이미 수리 요청한 기록입니다."),
    INVALID_REPAIR_STATUS(HttpStatus.BAD_REQUEST, "수리 요청 상태가 유효하지 않습니다."),

    // District exception
    NOT_FOUND_DISTRICT(HttpStatus.NOT_FOUND, "해당 지역을 찾을 수 없습니다."),

    ;

    @Schema(description = "HTTP 응답 상태코드 (HttpStatus Code)", example = "404")
    private final HttpStatus httpStatus;

    @Schema(description = "응답 메세지", example = "리소스를 찾을 수 없습니다.")
    private final String message;
}
