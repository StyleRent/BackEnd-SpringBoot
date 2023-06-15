package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.ProductEntity.ProductImage;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductInformation;
import kr.stylerent.StyleRent.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    @Query(value = "select * from product_image where product_id = :currentId", nativeQuery = true)
    List<ProductImage> findAllImagesByProductId(Integer currentId);

    @Query(value = "select * from product_image where productid = :imageId", nativeQuery = true)
    Optional<ProductImage> findByImageId(Integer imageId);
}
