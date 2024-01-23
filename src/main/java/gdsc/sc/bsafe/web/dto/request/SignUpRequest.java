package gdsc.sc.bsafe.web.dto.request;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.global.auth.Password;

import static gdsc.sc.bsafe.global.auth.Password.ENCODER;

public record SignUpRequest(
        String id,

        String password,

        String email,

        String tel,

        String name
) {
    public User toUser() {
        return new User(
                id,
                Password.encrypt(password, ENCODER),
                tel,
                email,
                name
        );
    }
}
