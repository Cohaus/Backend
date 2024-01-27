package gdsc.sc.bsafe.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.enums.RecordType;
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
public class RecordItemResponse {

    @Schema(description = "기록 pk", example = "1")
    private Long record_id;

    @Schema(description = "유저 pk", example = "1")
    private String user_id;

    @Schema(description = "기록 유형", example = "AI")
    @Enumerated(EnumType.STRING)
    private RecordType type;

    @Schema(description = "이미지 path", example = "records/...")
    private String image;

    @Schema(description = "제목", example = "기록 제목")
    private String title;

    @Schema(description = "생성일", example = "yyyy-MM-dd kk:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created_at;

    @Schema(description = "수정일", example = "yyyy-MM-dd kk:mm:ss")
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
