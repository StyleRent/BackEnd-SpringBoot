package kr.stylerent.StyleRent.dto.ProductResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInformationResponse {
    private Integer productId;
    private String message;
    private String error;
}
