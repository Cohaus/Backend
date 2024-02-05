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

    @NotBlank
    String title;

    @NotBlank
    String detail;

    @NotBlank
    String category;

    @NotNull
    MultipartFile image;

}
