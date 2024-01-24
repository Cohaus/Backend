package gdsc.sc.bsafe.domain.enums;

public enum RepairStatus {
    REQUEST("요청"),
    PROCEEDING("진행"),
    COMPLETE("완료");

    private String description;
    RepairStatus(String description){this.description=description;}
}
