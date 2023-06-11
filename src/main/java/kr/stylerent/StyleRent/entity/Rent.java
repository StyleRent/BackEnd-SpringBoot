package kr.stylerent.StyleRent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rent")
public class Rent {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Boolean status;

    @Column
    private Date returnedTime;

    @Column(name = "rentedTime", nullable = false, updatable = false)
    @CreationTimestamp
    private Date rentedTime;

    // owner product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // owner
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "renter_id")
    private User renter;
}
