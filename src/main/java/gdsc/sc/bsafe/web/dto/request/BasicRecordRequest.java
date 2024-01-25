package gdsc.sc.bsafe.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicRecordRequest {

    @Schema(description = "제목", example = "제목")
    @NotBlank
    String title;

    @Schema(description = "내용", example = "내용")
    @NotBlank
    String detail;

    @Schema(description = "카테고리", example = "카테고리")
    @NotBlank
    String category;

    @NotNull
    MultipartFile image;

}
