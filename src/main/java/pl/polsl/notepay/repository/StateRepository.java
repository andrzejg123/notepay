package pl.polsl.notepay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.notepay.model.entity.State;
import pl.polsl.notepay.model.enumeration.StateEnum;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    State findByName(StateEnum name);

}
