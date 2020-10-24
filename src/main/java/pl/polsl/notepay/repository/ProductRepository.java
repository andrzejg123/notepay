package pl.polsl.notepay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.notepay.model.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
