package gdsc.sc.bsafe.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
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
        for (AIGrade grade : AIGrade.values()) {
            if (grade.description.equals(source)) {
                return grade;
            }
        }
        return null;
    }

    public static boolean isValidDescription(String description) {
        for (AIGrade grade : AIGrade.values()) {
            if (grade.getDescription().equalsIgnoreCase(description)) {
                return true;
            }
        }
        return false;
    }

}
