package gdsc.sc.bsafe.web.dto.response;

import gdsc.sc.bsafe.domain.enums.RecordType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AIRecordResponse {
    private Long recordId;

    @Enumerated(value = EnumType.STRING)
    private RecordType type = RecordType.AI;

    private Long userId;

    private String image;

    private String title;

    private String detail;

    private String category;

    private Integer grade;

}
