package gdsc.sc.bsafe.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.enums.RecordType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepairRecordResponse {

    @Schema(description = "수리 신청 기록 pk", example = "1")
    private Long record_id;

    @Schema(description = "기록 유형", example = "1")
    private RecordType type;

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

    @Schema(description = "등급", example = "1 이상 10 이하의 자연수")
    private Integer grade;

    @Schema(description = "생성일", example = "yyyy-MM-dd kk:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created_at;

    @Schema(description = "수정일", example = "yyyy-MM-dd kk:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updated_at;

    public RepairRecordResponse(Record record, String district) {
        this.record_id = record.getRecordId();
        this.user_id = record.getUser().getId();
        this.image = record.getImage();
        this.title = record.getTitle();
        this.detail = record.getDetail();
        this.district = district;
        if (record instanceof AIRecord){
            this.type = RecordType.AI;
            this.grade = ((AIRecord) record).getGrade();
        }
        else {
            this.type = RecordType.BASIC;
            this.grade = null;
        }
        this.created_at = record.getCreatedAt();
        this.updated_at = record.getUpdatedAt();
    }
}
