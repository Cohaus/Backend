package gdsc.sc.bsafe.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record LoginResponse(

        @Schema(description = "user의 pk", example = "1")
        Long userId,

        @Schema(description = "발급된 access token", example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken,

        @Schema(description = "발급된 refresh token", example = "eyJhbGciOiJIUzI1NiJ9...")
        String refreshToken
) {
}
