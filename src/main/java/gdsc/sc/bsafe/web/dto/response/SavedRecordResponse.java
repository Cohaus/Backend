package gdsc.sc.bsafe.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import gdsc.sc.bsafe.domain.AIRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SavedRecordResponse {

    @Schema(description = "저장 기록 pk", example = "1")
    private Long record_id;

    @Schema(description = "유저 id", example = "id1234")
    private String user_id;

    @Schema(description = "이미지 path", example = "records/...")
    private String image;

    @Schema(description = "제목", example = "저장 기록 제목")
    private String title;

    @Schema(description = "내용", example = "저장 기록 내용")
    private String detail;

    @Schema(description = "카테고리", example = "도배")
    private String category;

    @Schema(description = "등급", example = "우수/보통/불량")
    private String grade;

    @Schema(description = "생성일", example = "2024-01-01 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created_at;

    @Schema(description = "수정일", example = "2024-01-01 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updated_at;

    public SavedRecordResponse(AIRecord aiRecord) {
        this.record_id = aiRecord.getRecordId();
        this.user_id = aiRecord.getUser().getId();
        this.image = aiRecord.getImage();
        this.title = aiRecord.getTitle();
        this.detail = aiRecord.getDetail();
        this.category = aiRecord.getCategory();
        this.grade = aiRecord.getGrade().getDescription();
        this.created_at = aiRecord.getCreatedAt();
        this.updated_at = aiRecord.getUpdatedAt();
    }
}
