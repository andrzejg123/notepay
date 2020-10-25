package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.Repayment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class RepaymentDto {

    private Long id;

    private LocalDateTime repaymentDate;

    private Long initiatorId;

    private Long recipientId;

    private Boolean cancelled;

    private List<DetailedChargeRepaymentDto> chargeRepayments;

    public RepaymentDto(Repayment repayment) {
        this.id = repayment.getId();
        this.repaymentDate = repayment.getRepaymentDate();
        this.initiatorId = repayment.getInitiator().getId();
        this.recipientId = repayment.getRecipient().getId();
        this.cancelled = repayment.getCancelled();
        this.chargeRepayments = repayment.getChargeRepayments().stream().map(DetailedChargeRepaymentDto::new)
                .collect(Collectors.toList());
    }

}
