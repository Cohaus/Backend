package gdsc.sc.bsafe.dto.request;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.global.security.Password;

import static gdsc.sc.bsafe.global.security.Password.ENCODER;

public record SignUpRequest(
        String id,

        String password,

        String email,

        String name
) {
    public User toUser() {
        return new User(
                id,
                Password.encrypt(password, ENCODER),
                email,
                name
        );
    }
}
