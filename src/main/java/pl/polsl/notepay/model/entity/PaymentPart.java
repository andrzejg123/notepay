package pl.polsl.notepay.model.entity;

import lombok.*;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "payment_parts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentPart extends BaseEntity {

    @Column
    Double value;

    @ManyToOne(optional = false)
    private Payment payment;

    @ManyToOne
    private Product product;

}
