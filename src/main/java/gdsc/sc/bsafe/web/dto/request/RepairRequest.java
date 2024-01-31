package gdsc.sc.bsafe.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import gdsc.sc.bsafe.domain.Record;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepairRequest {

    @Schema(description = "제목", example = "제목")
    @NotBlank
    String title;

    @Schema(description = "내용", example = "내용")
    @NotBlank
    String detail;

    @Schema(description = "카테고리", example = "카테고리")
    @NotBlank
    String category;

    @Schema(description = "place_id")
    @NotBlank
    String place_id;

    @Schema(description = "상세 주소", example = "성남시 수정구 성남대로 1342")
    @NotBlank
    String address;

    @Schema(description = "행정 구역", example = "성남시 수정구 성남대로")
    @NotBlank
    String district;

    @Schema(description = "방문 희망일", example = "2024-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @NotNull
    LocalDate visit_date;

    public RepairRequest(LocalDate visitDate, String placeId, String address, String district) {
        this.visit_date = visitDate;
        this.place_id = placeId;
        this.address = address;
        this.district = district;
    }

}
