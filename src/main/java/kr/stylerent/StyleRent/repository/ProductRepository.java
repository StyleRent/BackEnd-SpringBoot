package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.controller.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
