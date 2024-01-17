package gdsc.sc.bsafe.dto.request;

import gdsc.sc.bsafe.domain.User;

public record SignUpRequest(
        String id,

        String password,

        String email,

        String name
) {
    public User toUser() {
        return new User(
                id,
                password,
                email,
                name
        );
    }
}
