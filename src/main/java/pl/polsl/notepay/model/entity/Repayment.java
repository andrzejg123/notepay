package pl.polsl.notepay.model.entity;

import lombok.*;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "repayments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repayment extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime repaymentDate;

    @Column(nullable = false)
    private Boolean cancelled;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User initiator;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User recipient;

    @OneToMany(mappedBy = "repayment")
    private Set<ChargeRepayment> chargeRepayments;

}
