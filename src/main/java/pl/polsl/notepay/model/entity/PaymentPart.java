package pl.polsl.notepay.model.entity;

import lombok.*;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "payment_parts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentPart extends BaseEntity {

    @Column
    Double value;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

}
