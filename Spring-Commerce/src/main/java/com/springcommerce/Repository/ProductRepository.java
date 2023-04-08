package com.springcommerce.Repository;

import com.springcommerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%"
            + " OR p.brand.name LIKE %?1%" + "OR p.category.name LIKE %?1%"
            + " OR CONCAT(p.price, '') LIKE %?1%")
    List<Product> searchProductByKeyword(String keyword);

}
