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
public class PredictGradeRequest {

    @Schema(description = "카테고리, CRACK / PEELING / EXPOSED", example = "CRACK")
    @EnumValid(enumClass = RepairCategory.class)
    @NotBlank
    String category;

    @Schema(description = "사진", example = "MultipartFile")
    @NotNull
    MultipartFile image;
}
