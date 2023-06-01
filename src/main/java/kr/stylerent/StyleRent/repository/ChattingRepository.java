package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.Fav;
import kr.stylerent.StyleRent.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChattingRepository extends JpaRepository<Fav, Integer> {

}
