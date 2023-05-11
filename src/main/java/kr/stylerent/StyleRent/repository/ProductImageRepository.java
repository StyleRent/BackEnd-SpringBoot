package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.ProductEntity.ProductImage;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
}
