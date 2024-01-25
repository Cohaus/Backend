package gdsc.sc.bsafe.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AIRecordRequest {

    @Schema(description = "제목", example = "제목")
    @NotBlank
    String title;

    @Schema(description = "내용", example = "내용")
    @NotBlank
    String detail;

    @NotNull(message = "1이상 10이하의 자연수로 입력해야 합니다.")
    @Min(value = 1, message = "1이상 10이하의 자연수로 입력해야 합니다.")
    @Max(value = 10, message = "1이상 10이하의 자연수로 입력해야 합니다.")
    @Schema(title = "등급", description = "1 이상 10 이하의 자연수만 입력 가능합니다.")
    Integer grade;

    @Schema(description = "카테고리", example = "카테고리")
    @NotBlank
    String category;

    @NotNull
    MultipartFile image;

}
