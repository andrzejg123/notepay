package pl.polsl.notepay.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "payments")
@NoArgsConstructor
public class Payment extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Min(0)
    private Double ownerInvolveLevel;

    @Column(nullable = false)
    @Min(0)
    @Max(1)
    private Double ownerProgress;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Integer membersNumber;

    @ManyToOne(optional = false)
    private State state;

    @ManyToOne
    private Group group;

    @OneToMany(mappedBy = "payment")
    private Set<Charge> charges;

    @OneToMany(mappedBy = "payment")
    private List<PaymentPart> paymentParts;
}
