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
    private Double value;

    @Column
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Payment payment;

}
