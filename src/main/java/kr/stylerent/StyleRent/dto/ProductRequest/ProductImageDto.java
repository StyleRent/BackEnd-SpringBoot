package kr.stylerent.StyleRent.dto.ProductRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDto {
    private Integer productId;
    private byte[] imageByte;
}
