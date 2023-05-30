package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select * from product where user_id = :currentId", nativeQuery = true)
    List<Product> findAllById(Integer currentId);
}
