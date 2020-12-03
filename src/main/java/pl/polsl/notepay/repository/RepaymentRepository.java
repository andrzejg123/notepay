package pl.polsl.notepay.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.notepay.model.entity.Repayment;

import java.util.Optional;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {

    @EntityGraph(value = "Repayment.chargeRepayments.charges.payment")
    Optional<Repayment> findByIdIs(Long id);

}
