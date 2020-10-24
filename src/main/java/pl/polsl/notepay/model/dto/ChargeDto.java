package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.Charge;

@Data
@NoArgsConstructor
public class ChargeDto {

    private Long id;

    private Double progressLevel;

    private Double amount;

    private Double involveLevel;

    private Long idUser;

    private Long idPayment;

    public ChargeDto(Charge charge) {
        this.id = charge.getId();
        this.progressLevel = charge.getProgressLevel();
        this.amount = charge.getAmount();
        this.involveLevel = charge.getInvolveLevel();
        this.idUser = charge.getUser().getId();
        this.idPayment = charge.getPayment().getId();
    }

}
