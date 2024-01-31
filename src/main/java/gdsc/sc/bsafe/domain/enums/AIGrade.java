package gdsc.sc.bsafe.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum AIGrade {
    EXCELLENT("우수"),
    NORMAL("보통"),
    DEFECTIVE("불량");

    private String description;

    AIGrade(String description) {
        this.description = description;
    }

    @JsonCreator
    public static AIGrade convert(String source)
    {
        for (AIGrade messageType : AIGrade.values()) {
            if (messageType.name().equals(source)) {
                return messageType;
            }
        }
        return null;
    }
}
