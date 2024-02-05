package gdsc.sc.bsafe.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RepairCategory {
    CRACK("균열"),
    PEELING("박리"),
    EXPOSED("철근 노출"),
    FINISHING("마감"),
    SITE("대지"),
    RESIDENTIAL("생활"),
    WINDOW("창호");

    private String description;

    RepairCategory(String description) {
        this.description = description;
    }

    @JsonCreator
    public static RepairCategory convert(String source)
    {
        for (RepairCategory category : RepairCategory.values()) {
            if (category.description.equals(source)) {
                return category;
            }
        }
        return null;
    }

    public static boolean isValidDescription(String description) {
        for (RepairCategory category : RepairCategory.values()) {
            if (category.getDescription().equalsIgnoreCase(description)) {
                return true;
            }
        }
        return false;
    }
}
