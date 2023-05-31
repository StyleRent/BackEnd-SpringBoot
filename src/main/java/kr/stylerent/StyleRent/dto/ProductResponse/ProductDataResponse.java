package kr.stylerent.StyleRent.dto.ProductResponse;

import kr.stylerent.StyleRent.entity.ProductEntity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDataResponse {
    private Integer productId;
    private String productName;
    private String productInfo;
    private String productPrice;
    private List<ProductImageResponse> imagePath;
}
