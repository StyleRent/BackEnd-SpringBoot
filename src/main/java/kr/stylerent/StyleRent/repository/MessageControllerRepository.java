package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.MessageController;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageControllerRepository extends JpaRepository<MessageController, Integer> {
}
