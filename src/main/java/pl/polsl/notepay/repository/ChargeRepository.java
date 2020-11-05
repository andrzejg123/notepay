package pl.polsl.notepay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.notepay.model.entity.Charge;
import pl.polsl.notepay.model.entity.Payment;
import pl.polsl.notepay.model.entity.User;

import java.util.Collection;
import java.util.List;

public interface ChargeRepository extends JpaRepository<Charge, Long> {

    List<Charge> findAllByPaymentInAndProgressLevelBeforeAndUser(Collection<Payment> payments, Double progressLevelBound, User user);

}
