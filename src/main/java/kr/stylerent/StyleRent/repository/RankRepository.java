package kr.stylerent.StyleRent.repository;


import kr.stylerent.StyleRent.entity.Rank;
import kr.stylerent.StyleRent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Integer> {
    @Query(value = "select * from rank where user_id = :currentId", nativeQuery = true)
    Optional<Rank> findById(Integer currentId);

    @Query(value = "select * from rank where user_id = :currentId AND receiver_id = :receiverId", nativeQuery = true)
    Optional<Rank> checkUnique(Integer currentId, Integer receiverId);
}
