package gdsc.sc.bsafe.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {

    USER ("ROLE_USER"),
    VOLUNTEER ("ROLE_VOLUNTEER"),
    ADMIN("ROLE_ADMIN")
    ;

    private final String authority;
}