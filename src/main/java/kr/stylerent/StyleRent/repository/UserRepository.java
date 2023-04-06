package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query(value = "select * from user where id = :currentId", nativeQuery = true)
    Optional<User> findById(Integer currentId);
}
