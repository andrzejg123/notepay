package pl.polsl.notepay.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.notepay.exception.NotAuthorizedActionException;
import pl.polsl.notepay.exception.ResourceNotFoundException;
import pl.polsl.notepay.exception.WrongRequestException;
import pl.polsl.notepay.model.dto.PaymentDto;
import pl.polsl.notepay.model.dto.SimplePaymentDto;
import pl.polsl.notepay.model.entity.*;
import pl.polsl.notepay.model.enumeration.StateEnum;
import pl.polsl.notepay.repository.*;
import pl.polsl.notepay.service.PaymentService;
import pl.polsl.notepay.util.AuthenticationUtils;
import pl.polsl.notepay.util.DoubleUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final AuthenticationUtils authenticationUtils;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    //private final StateRepository stateRepository;
    private final ChargeRepository chargeRepository;
    private final PaymentPartRepository paymentPartRepository;

    private List<PaymentPart> buildPaymentParts(Payment payment, PaymentDto paymentDto) {

        if (paymentDto.getPaymentParts() != null && !paymentDto.getPaymentParts().isEmpty()) {

            final Double[] partsSum = {0.0d};
            List<PaymentPart> paymentParts = paymentDto.getPaymentParts().stream().map(part -> {

                partsSum[0] += part.getValue();

                return PaymentPart.builder()
                        .value(part.getValue())
                        .payment(payment)
                        .name(part.getName())
                        .build();
            }).collect(Collectors.toList());

            if (!DoubleUtil.equals(partsSum[0], paymentDto.getAmount()))
                throw new WrongRequestException("Wrong payment parts");

            return paymentParts;
        }

        return Collections.emptyList();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private Set<Charge> buildCharges(Double totalInvolveLevel, List<User> paymentMembers,
                                     Payment payment, PaymentDto paymentDto) {
        return paymentDto.getCharges().stream()
                .map(chargeDto -> {
                    final Double normalizedInvolveLevel = chargeDto.getInvolveLevel() / totalInvolveLevel;
                    return Charge.builder()
                            .amount(paymentDto.getAmount() * normalizedInvolveLevel)
                            .involveLevel(normalizedInvolveLevel)
                            .progressLevel(0.0d)
                            .payment(payment)
                            .user(paymentMembers.stream()
                                    .filter(it -> it.getId().equals(chargeDto.getIdUser()))
                                    .findFirst()
                                    .get()
                            )
                            .build();
                }).collect(Collectors.toSet());
    }

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto, String token) {
        if (paymentDto.getDescription() == null || "".equals(paymentDto.getDescription()) ||
                paymentDto.getOwnerInvolveLevel() == null || paymentDto.getOwnerInvolveLevel() < 0.0d ||
                paymentDto.getAmount() == null || paymentDto.getAmount() < 0.0d || paymentDto.getCharges() == null ||
                paymentDto.getCharges().isEmpty()) {
            throw new WrongRequestException("Required fields does not match requirements");
        }

        Payment payment = new Payment();

        Group group = null;
        if (paymentDto.getIdGroup() != null)
            group = groupRepository.findById(paymentDto.getIdGroup()).orElseThrow(() ->
                    new ResourceNotFoundException("There is no group with such an id"));

        User currentUser = authenticationUtils.getUserFromToken(token);

        List<PaymentPart> paymentParts = buildPaymentParts(payment, paymentDto);

        final Double[] totalInvolveLevel = {paymentDto.getOwnerInvolveLevel()};
        List<Long> paymentMembersIds = paymentDto.getCharges().stream()
                .map(chargeDto -> {
                    if (chargeDto.getInvolveLevel() <= 0.0d)
                        throw new WrongRequestException("Wrong involve levels");

                    totalInvolveLevel[0] += chargeDto.getInvolveLevel();
                    return chargeDto.getIdUser();
                })
                .collect(Collectors.toList());

        final Double ownerNormalizedInvolveLevel = paymentDto.getOwnerInvolveLevel() / totalInvolveLevel[0];

        List<User> paymentMembers = userRepository.findAllById(paymentMembersIds);
        if (paymentMembers.size() != paymentMembersIds.size() || paymentMembers.contains(currentUser))
            throw new WrongRequestException("Wrong user ids");

        Set<Charge> charges = buildCharges(totalInvolveLevel[0], paymentMembers, payment, paymentDto);

        payment.setDescription(paymentDto.getDescription());
        payment.setOwner(currentUser);
        payment.setAmount(paymentDto.getAmount());
        payment.setOwnerProgress(0.0d);
        payment.setCreateDate(LocalDateTime.now());
        payment.setOwnerInvolveLevel(ownerNormalizedInvolveLevel);
        payment.setMembersNumber(paymentMembersIds.size() + (payment.getOwnerInvolveLevel() > 0.0d ? 1 : 0));
        payment.setOwnerAmount(paymentDto.getAmount() * ownerNormalizedInvolveLevel);
        payment.setTotalProgress(0.0d);
        payment.setGroup(group);
        payment.setPaymentParts(paymentParts);
        payment.setCharges(charges);
        payment.setDeleted(false);
        //payment.setState(stateRepository.findByName(StateEnum.NEW));

        return new PaymentDto(paymentRepository.save(payment));
    }

    @Override
    public List<SimplePaymentDto> getOwnPayments(String token) {

        List<Payment> payments = authenticationUtils.getUserFromToken(token)
                .getPayments();

        return payments.stream().map(SimplePaymentDto::new).collect(Collectors.toList());
    }

    @Override
    public PaymentDto getPaymentDetails(Long idPayment, String token) {
        User currentUser = authenticationUtils.getUserFromToken(token);
        Payment payment = paymentRepository.findById(idPayment).orElseThrow(() ->
                new ResourceNotFoundException("There is no payment with such an id"));

        if(!payment.getOwner().equals(currentUser)) {
            throw new NotAuthorizedActionException("You are not authorized to get this payment's details");
        }

        return new PaymentDto(payment);
    }

}
