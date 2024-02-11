package gdsc.sc.bsafe.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Schema(description = "기록 pk, 수리 신청일 경우 수리 pk/ 저장 기록일 경우 기록 pk를 반환합니다.", example = "1")
    private Long id;

    @Schema(description = "기록 유형", example = "AI")
    @Enumerated(EnumType.STRING)
    private RecordType type;

    @Schema(description = "이미지 path", example = "records/...")
    private String image;

    @Schema(description = "제목", example = "기록 제목")
    private String title;

    @Schema(description = "생성일", example = "2024-01-01 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created_at;

    @Schema(description = "수정일", example = "2024-01-01 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updated_at;

    public RecordItemResponse(Record record, RecordType type) {
        this.id = record.getRecordId();
        this.type = type;
        this.image = record.getImage();
        this.title = record.getTitle();
        this.created_at = record.getCreatedAt();
        this.updated_at = record.getUpdatedAt();
    }

    public RecordItemResponse(Long repairId, Record record, RecordType type) {
        this.id = repairId;
        this.type = type;
        this.image = record.getImage();
        this.title = record.getTitle();
        this.created_at = record.getCreatedAt();
        this.updated_at = record.getUpdatedAt();
    }

}
