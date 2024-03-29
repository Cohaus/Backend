package gdsc.sc.bsafe.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.enums.RecordType;
import gdsc.sc.bsafe.domain.enums.RepairStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepairRecordResponse {

    @Schema(description = "수리 신청 기록 pk", example = "1")
    private Long record_id;

    @Schema(description = "기록 유형", example = "AI")
    private RecordType type;

    @Schema(description = "수리 진행 상태, 기록 작성자일 경우 null 반환", example = "REQUEST")
    @Enumerated(EnumType.STRING)
    private RepairStatus status;

    @Schema(description = "유저 id", example = "id1234")
    private String user_id;

    @Schema(description = "이미지 path", example = "records/...")
    private String image;

    @Schema(description = "제목", example = "수리 신청 기록 제목")
    private String title;

    @Schema(description = "내용", example = "수리 신청 기록 내용")
    private String detail;

    @Schema(description = "주소", example = "성남시 수정구")
    private String district;

    @Schema(description = "등급, BASIC 기록일 시 null 값을 반환합니다.", example = "우수/보통/불량")
    private String grade;

    @Schema(description = "생성일", example = "2024-01-01 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created_at;

    @Schema(description = "수정일", example = "2024-01-01 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updated_at;

    public RepairRecordResponse(Record record, RepairStatus status, String district, String grade, RecordType type) {
        this.record_id = record.getRecordId();
        this.user_id = record.getUser().getId();
        this.image = record.getImage();
        this.title = record.getTitle();
        this.detail = record.getDetail();
        this.district = district;
        this.grade = grade;
        this.type = type;
        this.status = status;
        this.created_at = record.getCreatedAt();
        this.updated_at = record.getUpdatedAt();
    }
}
