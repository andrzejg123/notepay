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
@NamedEntityGraph(
        name = "Repayment.chargeRepayments.charges.payment",
        attributeNodes = @NamedAttributeNode(value = "chargeRepayments", subgraph = "sub.ChargeRepayment.charge"),
        subgraphs = {
                @NamedSubgraph(name = "sub.ChargeRepayment.charge",
                        attributeNodes = @NamedAttributeNode(value = "charge", subgraph = "sub.Charge.payment")),
                @NamedSubgraph(name = "sub.Charge.payment",
                        attributeNodes = @NamedAttributeNode(value = "payment"))
        }
)
public class Repayment extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime repaymentDate;

    @Column(nullable = false)
    private Boolean cancelled;

    @Column(nullable = false)
    private Boolean deleted;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User initiator;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User recipient;

    @OneToMany(mappedBy = "repayment", cascade = CascadeType.ALL)
    private Set<ChargeRepayment> chargeRepayments;

}
