package gdsc.sc.bsafe.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import gdsc.sc.bsafe.domain.mapping.Repair;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepairItemResponse {

    @Schema(description = "수리 기록 pk", example = "1")
    private Long repair_id;

    @Schema(description = "이미지 path", example = "records/...")
    private String image;

    @Schema(description = "제목", example = "수리 제목")
    private String title;

    @Schema(description = "지역", example = "서울시 송파구 잠실동")
    private String district;

    @Schema(description = "방문 희망일", example = "2024-01-25")
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updated_at;

    public RepairItemResponse(Repair repair) {
        this.repair_id = repair.getRepairId();
        this.image = repair.getRecord().getImage();
        this.title = repair.getRecord().getTitle();
        this.district = repair.getDistrict();
        this.date = repair.getVisitDate();
    }

}
