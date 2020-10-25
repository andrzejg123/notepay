package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.ChargeRepayment;

@Data
@NoArgsConstructor
public class DetailedChargeRepaymentDto {

    private Double progressDelta;

    private Double chargeAmount;

    private String paymentDescription;

    public DetailedChargeRepaymentDto(ChargeRepayment chargeRepayment) {
        this.progressDelta = chargeRepayment.getProgressDelta();
        this.chargeAmount = chargeRepayment.getCharge().getAmount();
        this.paymentDescription = chargeRepayment.getCharge().getPayment().getDescription();
    }

}
