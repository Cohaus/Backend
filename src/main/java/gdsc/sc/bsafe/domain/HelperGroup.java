package gdsc.sc.bsafe.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name= "helper_group")
public class HelperGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "helper_group_id")
    private Long recordId;

    @Column(nullable = false)
    private String name;

}
