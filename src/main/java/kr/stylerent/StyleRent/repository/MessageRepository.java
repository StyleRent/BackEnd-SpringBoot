package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.MessageInit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageInit, Integer> {
    @Query(value = "select * from message where user_id = :senderId or receiver_id = :receiverId", nativeQuery = true)
    List<MessageInit> findMessage(Integer senderId, Integer receiverId);

    @Query(value = "SELECT * FROM message WHERE (user_id = :senderId OR receiver_id = :senderId) AND product_id = :productId LIMIT 1", nativeQuery = true)
    Optional<MessageInit> checkMessage(Integer senderId, Integer productId);

    @Query(value = "select * from message where user_id = :senderId and product_id = :productId", nativeQuery = true)
    Optional<MessageInit> checkMessageBySender(Integer senderId, Integer productId);

    @Query(value = "select * from message where receiver_id = :receiverId and product_id = :productId", nativeQuery = true)
    Optional<MessageInit> checkMessageByReceiver(Integer receiverId, Integer productId);

    @Query(value = "select * from message where product_id = :productId", nativeQuery = true)
    List<MessageInit> checkMessageByProductId(Integer productId);
}
