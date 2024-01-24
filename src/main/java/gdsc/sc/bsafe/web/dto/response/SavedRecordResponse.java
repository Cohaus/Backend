package gdsc.sc.bsafe.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SavedRecordResponse {
    private Long record_id;

    private String user_id;

    private String image;

    private String title;

    private String detail;

    private String category;

    private Integer grade;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updated_at;

    public SavedRecordResponse(AIRecord aiRecord) {
        this.record_id = aiRecord.getRecordId();
        this.user_id = aiRecord.getUser().getId();
        this.image = aiRecord.getImage();
        this.title = aiRecord.getTitle();
        this.detail = aiRecord.getDetail();
        this.category = aiRecord.getCategory();
        this.grade = aiRecord.getGrade();
        this.created_at = aiRecord.getCreatedAt();
        this.updated_at = aiRecord.getUpdatedAt();
    }
}
