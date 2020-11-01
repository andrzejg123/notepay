package pl.polsl.notepay.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.notepay.exception.ResourceNotFoundException;
import pl.polsl.notepay.exception.WrongRequestException;
import pl.polsl.notepay.model.dto.PaymentDto;
import pl.polsl.notepay.model.entity.*;
import pl.polsl.notepay.repository.GroupRepository;
import pl.polsl.notepay.repository.PaymentRepository;
import pl.polsl.notepay.repository.UserRepository;
import pl.polsl.notepay.service.PaymentService;
import pl.polsl.notepay.util.AuthenticationUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final AuthenticationUtils authenticationUtils;
    private final PaymentRepository paymentRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto, String token) {
        if (paymentDto.getDescription() == null || "".equals(paymentDto.getDescription()) ||
                paymentDto.getOwnerInvolveLevel() == null || paymentDto.getOwnerInvolveLevel() < 0 ||
                paymentDto.getAmount() == null || paymentDto.getAmount() < 0 || paymentDto.getCharges() == null ||
                paymentDto.getCharges().isEmpty()) {
            throw new WrongRequestException("Required fields does not match requirements");
        }

        Payment payment = new Payment();

        Group group = null;
        if (paymentDto.getIdGroup() != null)
            group = groupRepository.findById(paymentDto.getIdGroup()).orElseThrow(() ->
                    new ResourceNotFoundException("There is no group with such an id"));

        User currentUser = authenticationUtils.getUserFromToken(token);

        List<PaymentPart> paymentParts = new ArrayList<>();
        if (paymentDto.getPaymentParts() != null && !paymentDto.getPaymentParts().isEmpty()) {

            Set<Product> userProducts = currentUser.getProducts();
            paymentParts = paymentDto.getPaymentParts().stream().map(part ->
                    PaymentPart.builder()
                            .value(part.getValue())
                            .payment(payment)
                            .product(
                                userProducts.stream()
                                        .filter(idProduct -> idProduct.getId().equals(part.getIdProduct()))
                                        .findFirst()
                                        .orElseThrow(() -> new WrongRequestException("Wrong payment parts"))
                            ).build()
            ).collect(Collectors.toList());
        }

        final Double[] totalInvolveLevel = {paymentDto.getOwnerInvolveLevel()};

        List<Long> userIds = paymentDto.getCharges().stream()
                .map(chargeDto -> {
                    totalInvolveLevel[0] += chargeDto.getInvolveLevel();
                    return chargeDto.getIdUser();
                })
                .collect(Collectors.toList());

        Double normalizedMinimalAmount = paymentDto.getAmount() / totalInvolveLevel[0];

        List<User> users = userRepository.findAllById(userIds);

        Set<Charge> charges = paymentDto.getCharges().stream()
                .map(chargeDto ->
                        Charge.builder()
                                .amount(normalizedMinimalAmount * chargeDto.getInvolveLevel())
                                .involveLevel(chargeDto.getInvolveLevel())
                                .progressLevel(0.0)
                                .payment(payment)
                                .user(users.stream()
                                        .filter(it -> it.getId().equals(chargeDto.getIdUser()))
                                        .findFirst()
                                        .orElseThrow(() -> new WrongRequestException("Wrong user ids"))
                                ).build()
                ).collect(Collectors.toSet());

        payment.setDescription(paymentDto.getDescription());
        payment.setAmount(paymentDto.getAmount());
        payment.setCreateDate(LocalDateTime.now());
        payment.setMembersNumber(userIds.size());
        payment.setOwnerInvolveLevel(paymentDto.getOwnerInvolveLevel());
        payment.setOwnerAmount(normalizedMinimalAmount * paymentDto.getOwnerInvolveLevel());
        payment.setGroup(group);
        payment.setPaymentParts(paymentParts);
        payment.setCharges(charges);

        return new PaymentDto(paymentRepository.save(payment));
    }
}
