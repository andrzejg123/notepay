package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RepaymentRequestDto {

    private Long recipientId;

    private List<ChargeRepaymentDto> currentUserChargesRepayments;

    private List<ChargeRepaymentDto> recipientChargesRepayments;

}
