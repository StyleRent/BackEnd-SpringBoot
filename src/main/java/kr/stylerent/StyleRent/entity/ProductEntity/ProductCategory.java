package kr.stylerent.StyleRent.entity.ProductEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_category")
public class ProductCategory {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String category_name;
}
