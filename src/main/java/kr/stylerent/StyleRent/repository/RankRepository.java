package kr.stylerent.StyleRent.repository;


import kr.stylerent.StyleRent.entity.Rank;
import kr.stylerent.StyleRent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Integer> {
    @Query(value = "select * from rank where user_id = :currentId", nativeQuery = true)
    List<Rank> findAllById(Integer currentId);

    @Query(value = "select * from rank where receiver_id = :receiverId", nativeQuery = true)
    List<Rank> findAllByReceiverId(Integer receiverId);

    @Query(value = "select * from rank where product_id = :productId", nativeQuery = true)
    List<Rank> findAllByProductId(Integer productId);

    @Query(value = "select * from rank where user_id = :currentId AND receiver_id = :receiverId AND product_id = :productId", nativeQuery = true)
    Optional<Rank> checkUnique(Integer currentId, Integer receiverId, Integer productId);
}
