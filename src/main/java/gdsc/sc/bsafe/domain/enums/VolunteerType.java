package gdsc.sc.bsafe.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VolunteerType {

    SINGLE("개인 봉사자"),
    ORGANIZATION("기관 봉사자");

    private String description;

    VolunteerType(String description) {
        this.description = description;
    }

}
