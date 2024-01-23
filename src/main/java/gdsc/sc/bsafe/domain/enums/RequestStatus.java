package gdsc.sc.bsafe.domain.enums;

public enum RequestStatus {
    REQUEST("요청"),
    PROCEEDING("진행"),
    COMPLETE("완료");

    private String description;
    RequestStatus(String description){this.description=description;}
}
