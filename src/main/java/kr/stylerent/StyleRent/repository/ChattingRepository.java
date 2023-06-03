package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.Chatting;
import kr.stylerent.StyleRent.entity.Fav;
import kr.stylerent.StyleRent.entity.Location;
import kr.stylerent.StyleRent.entity.MessageInit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChattingRepository extends JpaRepository<Chatting, Integer> {
    @Query(value = "select * from chat where message_id = :messageId", nativeQuery = true)
    List<Chatting> findAllByMessageId(Integer messageId);

    @Query(value = "select * from chat where message_id = :messageId and sender_id = :senderId", nativeQuery = true)
    List<Chatting> findAllBySenderId(Integer messageId, Integer senderId);
}
