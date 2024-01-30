package gdsc.sc.bsafe.domain.enums;

import lombok.Getter;

@Getter
public enum RepairStatus {
    REQUEST("신청"),
    PROCEEDING("진행"),
    COMPLETE("완료");

    private String description;
    RepairStatus(String description){this.description=description;}
}
