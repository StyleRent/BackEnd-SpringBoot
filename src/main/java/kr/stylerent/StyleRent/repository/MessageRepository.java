package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.MessageInit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageInit, Integer> {
    @Query(value = "select * from message where user_id = :senderId or receiver_id = :receiverId", nativeQuery = true)
    List<MessageInit> findMessage(Integer senderId, Integer receiverId);

    @Query(value = "select * from message where user_id = :senderId and receiver_id = :receiverId and product_id = :productId", nativeQuery = true)
    Optional<MessageInit> checkMessageHistory(Integer senderId, Integer receiverId, Integer productId);
}
