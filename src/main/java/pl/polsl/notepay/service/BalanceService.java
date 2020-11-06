package pl.polsl.notepay.service;

import pl.polsl.notepay.model.dto.BalanceDto;

import java.util.List;

public interface BalanceService {

    BalanceDto getBalanceWithUser(Long idUser, String token);

    List<BalanceDto> getBalanceWithGroupMembers(Long idGroup, String token);
}
