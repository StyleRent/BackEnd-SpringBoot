package kr.stylerent.StyleRent.entity.ProductEntity;

import jakarta.persistence.*;
import kr.stylerent.StyleRent.entity.Product;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductCategory;
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
@Table(name = "product_information")
public class ProductInformation {
    @Id
    @Column(name = "productid")
    private Integer id; // 12

    @Column
    private String name;// 모자

    @OneToOne
    @MapsId
    @JoinColumn(name = "productid")
    private Product product;

//    @OneToOne
//    @Column
//    private ProductCategory category;

    @Column
    private Double price;

    @Column
    private String description;
}
