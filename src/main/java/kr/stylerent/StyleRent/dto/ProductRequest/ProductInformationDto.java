package kr.stylerent.StyleRent.dto.ProductRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInformationDto {
    private Integer productId;
    private String productName;
    private String productCategory;
    private String productPrice;
    private String productDescription;
}
