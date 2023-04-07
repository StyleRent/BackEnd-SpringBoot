package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    @Query(value = "select * from userdata where userdataid = :currentId", nativeQuery = true)
    Optional<UserData> findById(Integer currentId);
}
