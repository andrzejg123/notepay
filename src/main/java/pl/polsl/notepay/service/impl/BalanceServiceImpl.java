package pl.polsl.notepay.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.notepay.exception.NotAuthorizedActionException;
import pl.polsl.notepay.exception.ResourceNotFoundException;
import pl.polsl.notepay.model.dto.BalanceDto;
import pl.polsl.notepay.model.dto.DetailedChargeDto;
import pl.polsl.notepay.model.entity.Group;
import pl.polsl.notepay.model.entity.Payment;
import pl.polsl.notepay.model.entity.User;
import pl.polsl.notepay.repository.ChargeRepository;
import pl.polsl.notepay.repository.GroupRepository;
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
    private final GroupRepository groupRepository;

    private BalanceDto buildBalanceWithUser(User currentUser, User otherUser) {
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

    @Override
    public BalanceDto getBalanceWithUser(Long idUser, String token) {

        User currentUser = authenticationUtils.getUserFromToken(token);

        User otherUser = userRepository.findByIdIs(idUser).orElseThrow(() ->
                new ResourceNotFoundException("There is no user with such an id"));

        return buildBalanceWithUser(currentUser, otherUser);
    }

    @Override
    public List<BalanceDto> getBalanceWithGroupMembers(Long idGroup, String token) {
        Group group = groupRepository.findById(idGroup).orElseThrow(() ->
                new ResourceNotFoundException("There is no group with such an id"));

        User currentUser = authenticationUtils.getUserFromToken(token);
        List<User> groupMembers = userRepository.findAllByGroups(group);

        if (!groupMembers.contains(currentUser))
            throw new NotAuthorizedActionException("You do not belong to this group");

        return groupMembers
                .stream()
                .filter(member -> !member.equals(currentUser))
                .map(member -> buildBalanceWithUser(currentUser, member))
                .collect(Collectors.toList());
    }

}
