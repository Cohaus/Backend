package gdsc.sc.bsafe.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepairAIRecordRequest {

    @Schema(description = "제목", example = "제목")
    @NotBlank
    String title;

    @Schema(description = "내용", example = "내용")
    @NotBlank
    String detail;

    @Schema(description = "카테고리", example = "카테고리")
    @NotBlank
    String category;

    @Schema(title = "등급", description = "1 이상 10 이하의 자연수만 입력 가능합니다.")
    @NotNull(message = "1 이상 10 이하의 자연수로 입력해야 합니다.")
    @Min(value = 1, message = "1 이상 10 이하의 자연수로 입력해야 합니다.")
    @Max(value = 10, message = "1 이상 10 이하의 자연수로 입력해야 합니다.")
    Integer grade;

    @Schema(description = "place_id")
    @NotBlank
    String place_id;

    @Schema(description = "상세 주소", example = "성남시 수정구 성남대로 1342")
    @NotBlank
    String address;

    @Schema(description = "상세 정보", example = "성남시 수정구 성남대로")
    @NotBlank
    String district;

    @Schema(description = "방문 희망일", example = "2024-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @NotNull
    LocalDate date;

    @Schema(description = "사진", example = "MultipartFile")
    @NotNull
    MultipartFile image;

}
