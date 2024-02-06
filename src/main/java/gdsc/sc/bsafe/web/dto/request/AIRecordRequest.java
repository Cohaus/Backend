package gdsc.sc.bsafe.web.dto.request;

import gdsc.sc.bsafe.domain.enums.RepairCategory;
import gdsc.sc.bsafe.global.annotation.EnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AIRecordRequest {

    @Schema(description = "제목", example = "제목")
    @NotBlank
    String title;

    @Schema(description = "내용", example = "내용")
    @NotBlank
    String detail;

    @Schema(description = "등급, 우수 / 보통 / 불량 중 하나의 값을 입력합니다.", example = "보통")
    @NotNull
    String grade;

    @Schema(description = "카테고리, CRACK / PEELING / EXPOSED / FINISHING / SITE / RESIDENTIAL / WINDOW", example = "CRACK")
    @EnumValid(enumClass = RepairCategory.class)
    @NotBlank
    String category;

    @NotNull
    MultipartFile image;

}
