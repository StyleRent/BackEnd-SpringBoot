package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.Product;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductInformation;
import kr.stylerent.StyleRent.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductInformationRepository extends JpaRepository<ProductInformation, Integer> {
    @Query(value = "select * from product_information where productid = :currentId", nativeQuery = true)
    ProductInformation findInfoById(Integer currentId);

    @Query(value = "select * from product_information where name = :currentName", nativeQuery = true)
    List<ProductInformation> findALlByProductName(String currentName);
}