package pl.polsl.notepay.model.entity;

import lombok.*;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "charge_repayments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeRepayment extends BaseEntity {

    @Column
    private Double progressDelta;

    @ManyToOne
    private Repayment repayment;

    @ManyToOne
    private Charge charge;

}
