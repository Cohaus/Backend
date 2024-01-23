package gdsc.sc.bsafe.global.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // User exception
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),

    // Auth exception
    UNAUTHORIZED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Refresh Token이 유효하지 않습니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "해당 유저의 Refresh Token을 찾을 수 없습니다."),
    NOT_FOUND_TOKEN_DATA(HttpStatus.NOT_FOUND, "해당 유저의 토큰 정보를 찾을 수 없습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    INVALID_PERMISSION(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
