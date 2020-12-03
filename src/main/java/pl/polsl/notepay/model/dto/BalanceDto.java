package pl.polsl.notepay.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceDto {

    private Long idUser;

    private List<DetailedChargeDto> currentUserCharges;

    private List<DetailedChargeDto> otherUserCharges;

    private Double balanceSum;

}
