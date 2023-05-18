package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.Product;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductInformation;
import kr.stylerent.StyleRent.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductInformationRepository extends JpaRepository<ProductInformation, Integer> {
    @Query(value = "select * from product_information where productid = :currentId", nativeQuery = true)
    List<Rank> findAllById(Integer currentId);
}