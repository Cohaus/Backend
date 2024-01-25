package gdsc.sc.bsafe.domain;

import gdsc.sc.bsafe.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Table(name = "record")
public class Record extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long recordId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String detail;

    /*
    ENUM 타입으로 변경하기
     */
    private String category;

    public Record(User user, String image, String title, String detail, String category) {
        this.user = user;
        this.image = image;
        this.title = title;
        this.detail = detail;
        this.category = category;
    }

    public void updateSavedRecord(String title, String detail, String category) {
        this.title = title;
        this.detail = detail;
        this.category = category;
    }
}
