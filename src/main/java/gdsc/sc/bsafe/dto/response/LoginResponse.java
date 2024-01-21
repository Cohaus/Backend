package gdsc.sc.bsafe.dto.response;

public record LoginResponse(
        Long userId,

        String accessToken,

        String refreshToken
) {
}
