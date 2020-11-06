package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.Charge;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DetailedChargeDto extends ChargeDto {

    private String paymentDescription;

    private LocalDateTime paymentCreateDate;

    public DetailedChargeDto(Charge charge) {
        super(charge);
        this.paymentDescription = charge.getPayment().getDescription();
        this.paymentCreateDate = charge.getPayment().getCreateDate();
    }

}
