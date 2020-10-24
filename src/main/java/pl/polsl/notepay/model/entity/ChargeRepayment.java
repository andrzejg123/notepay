package pl.polsl.notepay.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "charge_repayments")
@Data
@NoArgsConstructor
public class ChargeRepayment extends BaseEntity {

    @Column
    private Double progressDelta;

    @ManyToOne
    private Repayment repayment;

    @ManyToOne
    private Charge charge;

}
