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
public class RepairResponse {
    private Long record_id;

    private String user_id;

    private String image;

    private String title;

    private String detail;

    private String category;

    private Integer grade = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updated_at;

    public RepairResponse(Record record) {
        this.record_id = record.getRecordId();
        this.user_id = record.getUser().getId();
        this.image = record.getImage();
        this.title = record.getTitle();
        this.detail = record.getDetail();
        this.category = record.getCategory();
        if(record instanceof AIRecord){
            this.grade = ((AIRecord) record).getGrade();
        }
        this.created_at = record.getCreatedAt();
        this.updated_at = record.getUpdatedAt();
    }
}
