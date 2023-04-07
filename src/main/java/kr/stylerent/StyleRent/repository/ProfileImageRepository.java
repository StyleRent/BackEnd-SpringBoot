package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.ProfileImage;
import kr.stylerent.StyleRent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Integer> {
    @Query(value = "select * from profile_image where userid = :currentId", nativeQuery = true)
    Optional<ProfileImage> findById(Integer currentId);
}