package pl.polsl.notepay.service;

import pl.polsl.notepay.model.dto.BalanceDto;

public interface BalanceService {

    BalanceDto getBalanceWithUser(Long idUser, String token);

}
