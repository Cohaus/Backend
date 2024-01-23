package gdsc.sc.bsafe.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HelperType {

    SINGLE("개인 봉사자"),
    GROUP("기관 봉사자");

    private String description;
    HelperType(String description){this.description=description;}
}
