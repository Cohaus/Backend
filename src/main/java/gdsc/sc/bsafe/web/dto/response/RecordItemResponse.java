package gdsc.sc.bsafe.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.enums.RecordType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecordItemResponse {

    private Long record_id;

    private String user_id;

    @Enumerated(EnumType.STRING)
    private RecordType type;

    private String image;

    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updated_at;

    public RecordItemResponse(Record record) {
        this.record_id = record.getRecordId();
        this.user_id = record.getUser().getId();
        this.image = record.getImage();
        this.title = record.getTitle();
        this.created_at = record.getCreatedAt();
        this.updated_at = record.getUpdatedAt();
        if (record instanceof AIRecord){
            this.type = RecordType.AI;
        }
        else this.type = RecordType.BASIC;
    }

}
