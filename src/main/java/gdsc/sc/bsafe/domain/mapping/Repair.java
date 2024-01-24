package gdsc.sc.bsafe.domain.mapping;

import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.common.BaseTimeEntity;
import gdsc.sc.bsafe.domain.enums.RepairStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "repair")
public class Repair extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repair_id")
    private Long repairId;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    @ManyToOne
    @JoinColumn(name = "volunteer_id", nullable = false)
    private User volunteer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepairStatus status;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String placeId;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String address;

    @Builder
    public Repair(Record record, User volunteer, RepairStatus status, LocalDate date, String placeId, String district, String address) {
        this.record = record;
        this.volunteer = volunteer;
        this.status = status;
        this.date = date;
        this.placeId = placeId;
        this.district = district;
        this.address = address;
    }
}
