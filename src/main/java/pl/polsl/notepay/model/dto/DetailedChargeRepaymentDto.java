package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.ChargeRepayment;

@Data
@NoArgsConstructor
public class DetailedChargeRepaymentDto extends ChargeRepaymentDto {

    private Double chargeAmount;

    private String paymentDescription;

    public DetailedChargeRepaymentDto(ChargeRepayment chargeRepayment) {
        super(chargeRepayment);
        this.chargeAmount = chargeRepayment.getCharge().getAmount();
        this.paymentDescription = chargeRepayment.getCharge().getPayment().getDescription();
    }

}
