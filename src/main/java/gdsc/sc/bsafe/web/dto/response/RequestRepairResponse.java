package gdsc.sc.bsafe.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import gdsc.sc.bsafe.domain.mapping.Repair;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Schema
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestRepairResponse {

    @Schema(description = "수리 기록 pk", example = "1")
    private Long repair_id;

    @Schema(description = "이미지 path", example = "records/...")
    private String image;

    @Schema(description = "제목", example = "수리 제목")
    private String title;

    @Schema(description = "카테고리", example = "도배")
    private String category;

    @Schema(description = "지역", example = "서울시 광진구 군자동")
    private String district;

    @Schema(description = "방문 희망일", example = "2024년 1월 25일")
    private String visit_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created_at;

    public RequestRepairResponse(Repair repair) {
        this.repair_id = repair.getRepairId();
        this.image = repair.getRecord().getImage();
        this.title = repair.getRecord().getTitle();
        this.category = repair.getRecord().getCategory();
        this.district = createLegalDistrictString(repair);
        this.visit_date = formatVisitDate(repair.getVisitDate());
        this.created_at = repair.getCreatedAt();
    }

    private String createLegalDistrictString(Repair repair) {
        return repair.getLegalDistrict().getSido() + " " + repair.getLegalDistrict().getGu() + " " + repair.getLegalDistrict().getDong();
    }

    private String formatVisitDate(LocalDate visitDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일");
        return visitDate.format(formatter);
    }

}