package pl.polsl.notepay.model.entity;

import lombok.*;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Double ownerAmount;

    @Column(nullable = false)
    private Integer membersNumber;

    @Column(nullable = false)
    private Double totalProgress;

    //@ManyToOne(optional = false)
    //private State state;

    @Column(nullable = false)
    private Boolean deleted;

    @ManyToOne
    private Group group;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private Set<Charge> charges;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<PaymentPart> paymentParts;
}
