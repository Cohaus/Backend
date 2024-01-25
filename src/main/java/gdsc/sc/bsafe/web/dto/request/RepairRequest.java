package gdsc.sc.bsafe.web.dto.request;

import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepairRequest {
    private Record record;
    private LocalDate date;
    private String placeId;
    private String address;
    private String district;
}
