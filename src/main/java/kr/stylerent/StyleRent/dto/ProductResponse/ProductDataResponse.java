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
    private Integer userId;
    private String productName;
    private String productInfo;
    private String productPrice;
    private Boolean liked;
    private Boolean rentStatus;
    private Double rankAverage;
    private List<ProductImageResponse> imagePath;
}
