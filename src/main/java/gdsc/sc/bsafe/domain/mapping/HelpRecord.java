package gdsc.sc.bsafe.domain.mapping;

import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.common.BaseTimeEntity;
import gdsc.sc.bsafe.domain.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "help_record")
public class HelpRecord extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "help_record_id")
    private Long helpRecordId;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    @ManyToOne
    @JoinColumn(name = "helper_id", nullable = false)
    private User helper;

    @Column(nullable = false)
    private RequestStatus status;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String placeId;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String address;

    @Builder
    public HelpRecord(Record record, User helper, RequestStatus status, Date date, String placeId, String district, String address) {
        this.record = record;
        this.helper = helper;
        this.status = status;
        this.date = date;
        this.placeId = placeId;
        this.district = district;
        this.address = address;
    }
}
