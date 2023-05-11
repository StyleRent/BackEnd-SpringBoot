package kr.stylerent.StyleRent.entity;

import jakarta.persistence.*;
import kr.stylerent.StyleRent.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private Integer productid;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
