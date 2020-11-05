package pl.polsl.notepay.model.entity;

import lombok.*;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@EqualsAndHashCode(exclude = {"payment"}, callSuper = true)
@Entity
@Getter
@Setter
@Table(name = "charges")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Charge extends BaseEntity {

    @Column(nullable = false)
    @Min(0)
    @Max(1)
    private Double progressLevel;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @Min(0)
    private Double involveLevel;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

}
