package gdsc.sc.bsafe.domain.mapping;

import gdsc.sc.bsafe.domain.District;
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
    @JoinColumn(name = "volunteer_id")
    private User volunteer;

    @Column(name = "proceed_date")
    private LocalDate proceedDate;

    @Column(name = "complete_date")
    private LocalDate completeDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepairStatus status;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Column(nullable = false)
    private String placeId;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District legalDistrict;

    @Builder
    public Repair(Record record, RepairStatus status, LocalDate visitDate, String placeId, String district, String address, District legalDistrict) {
        this.record = record;
        this.status = status;
        this.visitDate = visitDate;
        this.placeId = placeId;
        this.district = district;
        this.address = address;
        this.legalDistrict = legalDistrict;
    }

    public void updateRepairStatus(RepairStatus status) {
        this.status = status;
    }

    public void updateVolunteer(User user) {
        this.volunteer = user;
    }

    public void updateProceedDate(LocalDate proceedDate) {
        this.proceedDate = proceedDate;
    }

    public void updateCompleteDate(LocalDate completeDate) {
        this.completeDate = completeDate;
    }

}
