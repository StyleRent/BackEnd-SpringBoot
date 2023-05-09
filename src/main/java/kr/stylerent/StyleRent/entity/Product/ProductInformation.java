package kr.stylerent.StyleRent.entity.Product;

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
@Table(name = "information")
public class ProductInformation {
    @Id
    @Column(name = "productid")
    private Integer id; // 1

    @OneToOne
    @MapsId
    @JoinColumn(name = "productid")
    private Product product;

    @OneToOne
    @Column
    private ProductCategory category;

    @Column
    private Double price;

    @Column
    private String description;
}
