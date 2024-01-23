package gdsc.sc.bsafe.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {

    USER ("ROLE_USER"),
    HELPER ("ROLE_HELPER"),
    ADMIN("ROLE_ADMIN")
    ;

    private final String authority;
}