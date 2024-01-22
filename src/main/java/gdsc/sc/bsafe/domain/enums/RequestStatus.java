package gdsc.sc.bsafe.domain.enums;

public enum RequestStatus {
    REQUESTED("요청"),
    PROCEEDING("진행"),
    COMPLETED("완료");

    private String description;
    RequestStatus(String description){this.description=description;}
}
