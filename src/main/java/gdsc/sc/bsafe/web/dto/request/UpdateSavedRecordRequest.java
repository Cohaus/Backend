package gdsc.sc.bsafe.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSavedRecordRequest {
    @Schema(description = "제목", example = "제목")
    String title;

    @Schema(description = "내용", example = "내용")
    String detail;

    @Schema(description = "카테고리", example = "카테고리")
    String category;

}
