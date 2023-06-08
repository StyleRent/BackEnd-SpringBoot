package kr.stylerent.StyleRent.repository;

import kr.stylerent.StyleRent.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RentRepository extends JpaRepository<Rent, Integer> {
    @Query(value = "SELECT * FROM rent WHERE (user_id = :senderId OR renter_id = :renterId) AND product_id = :productId LIMIT 1", nativeQuery = true)
    Optional<Rent> checkRentStatus(Integer userId, Integer renterId, Integer productId);

    @Query(value = "SELECT * FROM rent WHERE renter_id = :renterId", nativeQuery = true)
    List<Rent> getMyRents(Integer renterId);
}