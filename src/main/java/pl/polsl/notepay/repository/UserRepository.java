package pl.polsl.notepay.repository;

import org.hibernate.annotations.LazyGroup;
import org.hibernate.annotations.LazyToOne;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.polsl.notepay.model.entity.Group;
import pl.polsl.notepay.model.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"payments"}, type = EntityGraph.EntityGraphType.LOAD)
    List<User> findAllByGroups(Group group);

    @EntityGraph(attributePaths = {"payments"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findByIdIs(Long id);

}
