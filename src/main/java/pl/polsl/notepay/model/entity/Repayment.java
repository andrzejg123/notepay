package pl.polsl.notepay.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "repayments")
@Data
@NoArgsConstructor
public class Repayment extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime repaymentDate;

    @Column(nullable = false)
    private Boolean cancelled;

    @ManyToOne(optional = false)
    private User initiator;

    @ManyToOne(optional = false)
    private User recipient;

    @OneToMany(mappedBy = "repayment")
    private Set<ChargeRepayment> chargeRepayments;

}
