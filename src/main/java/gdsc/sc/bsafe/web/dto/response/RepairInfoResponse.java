package gdsc.sc.bsafe.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.mapping.Repair;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RepairInfoResponse {

    @Schema(description = "수리 진행 상태", example = "REQUEST")
    private String repair_status;

    @Schema(description = "카테고리", example = "생활")
    private String category;

    @Schema(description = "수리 신청일", example = "24-01-25")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate request_date;

    @Schema(description = "진행 시작일(봉사자가 없을 경우 null을 반환합니다.)", example = "24-01-25")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate proceed_date;

    @Schema(description = "수리 완료일(완료되지 않았을 경우 null을 반환합니다.)", example = "24-01-25")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate complete_date;

    @Schema(description = "신청자 유저 pk", example = "1")
    private Long user_id;

    @Schema(description = "신청자 이름", example = "서주원")
    private String user_name;

    @Schema(description = "신청자 전화 번호", example = "010-1111-1111")
    private String user_tel;

    @Schema(description = "봉사자 유저 pk(봉사자가 없을 경우 null을 반환합니다.)", example = "2")
    private Long volunteer_id;

    @Schema(description = "봉사자 이름(봉사자가 없을 경우 null을 반환합니다.)", example = "김코하")
    private String volunteer_name;

    @Schema(description = "봉사자 전화 번호(봉사자가 없을 경우 null을 반환합니다.)", example = "010-2222-2222")
    private String volunteer_tel;

    @Schema(description = "방문 희망일", example = "24-01-25")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @Schema(description = "상세 주소", example = "경기도 성남시 수정구 성남대로 1342")
    private String address;

    public RepairInfoResponse(Repair repair, String category,Long user_id, String user_name, String user_tel, Long volunteer_id, String volunteer_name, String volunteer_tel, String address) {
        this.repair_status = repair.getStatus().getDescription();
        this.category = category;
        this.request_date = LocalDate.from(repair.getCreatedAt());
        this.proceed_date = repair.getProceedDate();
        this.complete_date = repair.getCompleteDate();
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_tel = user_tel;
        this.volunteer_id = volunteer_id;
        this.volunteer_name = volunteer_name;
        this.volunteer_tel = volunteer_tel;
        this.date = repair.getVisitDate();
        this.address = address;
    }
}
