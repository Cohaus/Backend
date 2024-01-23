package gdsc.sc.bsafe.web.dto.response;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.enums.RecordType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicRecordResponse {
    private Long recordId;

    @Enumerated(value = EnumType.STRING)
    private RecordType type = RecordType.BASIC;

    private Long userId;

    private String image;

    private String title;

    private String detail;

    private String category;

}
