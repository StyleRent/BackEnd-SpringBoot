package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.Fav;
import kr.stylerent.StyleRent.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavRepository extends JpaRepository<Fav, Integer> {
    @Query(value = "select * from favid where user_id = :currentId AND product_id = :productId", nativeQuery = true)
    Optional<Fav> checkCurrentLike(Integer currentId, Integer productId);

    @Query(value = "select * from favid where product_id = :productId", nativeQuery = true)
    List<Fav> getFavByProductId(Integer productId);

    @Query(value = "select * from favid where user_id = :currentId", nativeQuery = true)
    List<Fav> getMyFav(Integer currentId);
}
