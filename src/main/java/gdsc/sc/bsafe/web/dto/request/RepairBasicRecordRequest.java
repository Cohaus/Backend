package gdsc.sc.bsafe.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepairBasicRecordRequest {
    Long user_id;
    String title;

    String detail;

    String image;

    String category;

    String place_id;

    String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate date;

}
