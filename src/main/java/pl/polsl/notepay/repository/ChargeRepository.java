package pl.polsl.notepay.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.polsl.notepay.model.entity.Charge;
import pl.polsl.notepay.model.entity.Payment;
import pl.polsl.notepay.model.entity.User;

import java.util.Collection;
import java.util.List;

public interface ChargeRepository extends JpaRepository<Charge, Long> {

    List<Charge> findAllByPaymentInAndProgressLevelBeforeAndUser(Collection<Payment> payments,
                                                                 Double progressLevelBound, User user);

    @Query("select c from Charge c join Payment p where c.user = (:user) and p.deleted = false and p.owner = (:paymentsOwner) and c.progressLevel < 1.0")
    List<Charge> findAllByUserIsAndPaymentsOwnerIs(@Param("user") User user, @Param("paymentsOwner") User paymentsOwner);

    @EntityGraph(attributePaths = {"payment"})
    @Query("select c from Charge c join Payment p where p.owner = (:paymentOwner) and c.id in (:ids)")
    List<Charge> findAllByIdIsInAndPaymentsOwnerIs(@Param("ids") Collection<Long> ids,
                                                   @Param("paymentOwner") User paymentOwner);

}
