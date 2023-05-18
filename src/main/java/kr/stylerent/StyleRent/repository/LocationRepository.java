package kr.stylerent.StyleRent.repository;


import kr.stylerent.StyleRent.entity.Location;
import kr.stylerent.StyleRent.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
