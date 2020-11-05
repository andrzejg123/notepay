package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.Charge;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DetailedChargeDto extends ChargeDto {

    private String paymentDescription;

    public DetailedChargeDto(Charge charge) {
        super(charge);
        this.paymentDescription = charge.getPayment().getDescription();
    }

}
