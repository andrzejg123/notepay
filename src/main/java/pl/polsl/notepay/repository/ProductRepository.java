package pl.polsl.notepay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.polsl.notepay.model.entity.Product;
import pl.polsl.notepay.model.entity.User;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Set<Product> findByUser(User user);

}
