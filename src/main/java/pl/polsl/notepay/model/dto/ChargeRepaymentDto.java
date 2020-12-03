package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.ChargeRepayment;

@Data
@NoArgsConstructor
public class ChargeRepaymentDto {

    private Long id;

    private Long idCharge;

    private Double progressDelta;

    public ChargeRepaymentDto(ChargeRepayment chargeRepayment) {
        this.id = chargeRepayment.getId();
        this.idCharge = chargeRepayment.getCharge().getId();
        this.progressDelta = chargeRepayment.getProgressDelta();
    }

}
