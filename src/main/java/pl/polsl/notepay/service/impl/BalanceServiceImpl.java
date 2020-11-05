package pl.polsl.notepay.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.notepay.exception.ResourceNotFoundException;
import pl.polsl.notepay.model.dto.BalanceDto;
import pl.polsl.notepay.model.dto.DetailedChargeDto;
import pl.polsl.notepay.model.entity.Payment;
import pl.polsl.notepay.model.entity.User;
import pl.polsl.notepay.repository.ChargeRepository;
import pl.polsl.notepay.repository.UserRepository;
import pl.polsl.notepay.service.BalanceService;
import pl.polsl.notepay.util.AuthenticationUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final AuthenticationUtils authenticationUtils;
    private final UserRepository userRepository;
    private final ChargeRepository chargeRepository;

    @Override
    public BalanceDto getBalanceWithUser(Long idUser, String token) {

        User currentUser = authenticationUtils.getUserFromToken(token);

        User otherUser = userRepository.findById(idUser).orElseThrow(() ->
                new ResourceNotFoundException("There is no user with such an id"));

        List<Payment> currentUserPayments = currentUser.getPayments();
        List<Payment> otherUserPayments = otherUser.getPayments();

        return BalanceDto.builder()
                .idUser(otherUser.getId())
                .currentUserCharges(
                        chargeRepository.findAllByPaymentInAndProgressLevelBeforeAndUser(otherUserPayments,
                                1.0d, currentUser)
                                .stream()
                                .map(DetailedChargeDto::new)
                                .collect(Collectors.toList())
                )
                .otherUserCharges(
                        chargeRepository.findAllByPaymentInAndProgressLevelBeforeAndUser(currentUserPayments,
                                1.0d, otherUser)
                                .stream()
                                .map(DetailedChargeDto::new)
                                .collect(Collectors.toList())
                )
                .build();

    }

}
