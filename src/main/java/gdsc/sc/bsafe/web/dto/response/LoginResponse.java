package gdsc.sc.bsafe.web.dto.response;

public record LoginResponse(
        Long userId,

        String accessToken,

        String refreshToken
) {
}
