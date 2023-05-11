package kr.stylerent.StyleRent.entity.ProductEntity;

import jakarta.persistence.*;
import kr.stylerent.StyleRent.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_image")
public class ProductImage {
    @Id
    @GeneratedValue
    private Integer productid;

    @Column
    private String image_path;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
