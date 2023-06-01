package kr.stylerent.StyleRent.dto.ProductResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyFavResponse {
    private String productImage;
    private Integer productId;
    private String productName;
    private String productPrice;
}
