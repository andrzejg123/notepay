package pl.polsl.notepay.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.notepay.exception.NotAuthorizedActionException;
import pl.polsl.notepay.exception.ResourceNotFoundException;
import pl.polsl.notepay.exception.WrongRequestException;
import pl.polsl.notepay.model.dto.BalanceDto;
import pl.polsl.notepay.model.dto.ChargeRepaymentDto;
import pl.polsl.notepay.model.dto.RepaymentRequestDto;
import pl.polsl.notepay.model.entity.*;
import pl.polsl.notepay.repository.ChargeRepository;
import pl.polsl.notepay.repository.PaymentRepository;
import pl.polsl.notepay.repository.RepaymentRepository;
import pl.polsl.notepay.repository.UserRepository;
import pl.polsl.notepay.service.RepaymentService;
import pl.polsl.notepay.util.AuthenticationUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RepaymentServiceImpl implements RepaymentService {

    private final AuthenticationUtils authenticationUtils;
    private final RepaymentRepository repaymentRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ChargeRepository chargeRepository;

    private Double getBalanceSumWithUser(User currentUser, User otherUser) {

        final Double[] balanceSum = {0.0d};
        chargeRepository.findAllByUserIsAndPaymentsOwnerIs(currentUser, otherUser)
                .forEach(charge -> balanceSum[0] -= charge.getAmount() * (1.0d - charge.getProgressLevel()));
        chargeRepository.findAllByUserIsAndPaymentsOwnerIs(otherUser, currentUser)
                .forEach(charge -> balanceSum[0] += charge.getAmount() * (1.0d - charge.getProgressLevel()));

        return balanceSum[0];
    }

    private List<Charge> getRepaymentsCharges(List<ChargeRepaymentDto> chargesRepayments, User paymentsOwner,
                                              Map<Long, Double> progressDeltas) {
        List<Long> repaymentsChargesIds = chargesRepayments
                .stream()
                .peek(chargeRepaymentDto -> {
                    if (chargeRepaymentDto.getProgressDelta() <= 0.0d || chargeRepaymentDto.getProgressDelta() > 1.0d)
                        throw new WrongRequestException("Wrong progress deltas");

                    progressDeltas.put(chargeRepaymentDto.getIdCharge(), chargeRepaymentDto.getProgressDelta());
                })
                .map(ChargeRepaymentDto::getIdCharge)
                .collect(Collectors.toList());

        List<Charge> repaymentsCharges = chargeRepository
                .findAllByIdIsInAndPaymentsOwnerIs(repaymentsChargesIds, paymentsOwner);

        if (repaymentsCharges.size() != repaymentsChargesIds.size())
            throw new WrongRequestException("Wrong charges ids");

        return repaymentsCharges;
    }

    private ChargeRepayment processCharge(Charge charge, Map<Long, Double> progressDeltas, Repayment repayment) {
        Double chargeProgressLevel = charge.getProgressLevel();
        final Double progressDelta = progressDeltas.get(charge.getId());
        chargeProgressLevel += progressDelta;
        if (chargeProgressLevel > 1.0d)
            throw new WrongRequestException("Wrong progress deltas");

        charge.setProgressLevel(chargeProgressLevel);

        Payment payment = charge.getPayment();
        Double totalProgressLevel = payment.getTotalProgress();
        totalProgressLevel += progressDelta * charge.getInvolveLevel();

        payment.setTotalProgress(totalProgressLevel);
        //paymentRepository.save(payment);

        return ChargeRepayment.builder()
                .charge(charge)
                .repayment(repayment)
                .progressDelta(progressDelta)
                .build();
    }

    @Override
    public void requestForRepayment(RepaymentRequestDto repaymentRequestDto, String token) {

        User currentUser = authenticationUtils.getUserFromToken(token);
        User recipient = userRepository.findById(repaymentRequestDto.getRecipientId()).orElseThrow(() ->
                new ResourceNotFoundException("There is no user with such an id"));

        Map<Long, Double> recipientRepaymentsProgressDeltas = new HashMap<>();

        List<Charge> recipientRepaymentsCharges =
                getRepaymentsCharges(repaymentRequestDto.getRecipientChargesRepayments(),
                        currentUser, recipientRepaymentsProgressDeltas);

        Repayment repayment = new Repayment();

        Set<ChargeRepayment> chargeRepayments = recipientRepaymentsCharges.stream()
                .map(charge -> processCharge(charge, recipientRepaymentsProgressDeltas, repayment))
                .collect(Collectors.toSet());

        List<Charge> currentUserRepaymentsCharges = Collections.emptyList();
        if (repaymentRequestDto.getCurrentUserChargesRepayments().size() != 0) {

            Double balance = getBalanceSumWithUser(currentUser, recipient);
            if (balance <= 0.0d)
                throw new NotAuthorizedActionException("You cannot repay your charges unless you are leading in balance");

            Map<Long, Double> currentUserRepaymentsProgressDeltas = new HashMap<>();

            currentUserRepaymentsCharges =
                    getRepaymentsCharges(repaymentRequestDto.getCurrentUserChargesRepayments(),
                            recipient, currentUserRepaymentsProgressDeltas);

            chargeRepayments.addAll(
                    currentUserRepaymentsCharges.stream()
                            .map(charge -> processCharge(charge, currentUserRepaymentsProgressDeltas, repayment))
                            .collect(Collectors.toSet())
            );
        }

        repayment.setRepaymentDate(LocalDateTime.now());
        repayment.setChargeRepayments(chargeRepayments);
        repayment.setCancelled(false);
        repayment.setDeleted(false);
        repayment.setInitiator(currentUser);
        repayment.setRecipient(recipient);
        repaymentRepository.save(repayment);
        chargeRepository.saveAll(recipientRepaymentsCharges);
        chargeRepository.saveAll(currentUserRepaymentsCharges);
        paymentRepository.flush();
    }

    @Override
    public void cancelRepayment(Long idRepayment, String token) {
        User currentUser = authenticationUtils.getUserFromToken(token);
        Repayment repayment = repaymentRepository.findByIdIs(idRepayment).orElseThrow(() ->
                new ResourceNotFoundException("There is no repayment with such an id"));

        if(!repayment.getInitiator().equals(currentUser))
            throw new NotAuthorizedActionException("You cannot cancel this repayment");

        repayment.getChargeRepayments().forEach(chargeRepayment -> {
            Charge charge = chargeRepayment.getCharge();
            final Double progressDelta = chargeRepayment.getProgressDelta();
            charge.setProgressLevel(charge.getProgressLevel() - progressDelta);
            Payment chargePayment = charge.getPayment();
            chargePayment.setTotalProgress(chargePayment.getTotalProgress() - charge.getInvolveLevel() * progressDelta);
        });

        repayment.setCancelled(true);
        repaymentRepository.save(repayment);
        chargeRepository.flush();
        paymentRepository.flush();
    }

}
