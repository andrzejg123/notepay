package pl.polsl.notepay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.notepay.model.entity.Charge;
import pl.polsl.notepay.model.entity.Payment;
import pl.polsl.notepay.model.entity.PaymentPart;

import java.util.Collection;
import java.util.List;

@Repository
public interface PaymentPartRepository extends JpaRepository<PaymentPart, Long> {

    List<PaymentPart> findAllByPaymentIsIn(Collection<Payment> payments);

}
