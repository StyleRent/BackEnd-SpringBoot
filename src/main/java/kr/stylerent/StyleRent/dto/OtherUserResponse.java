package kr.stylerent.StyleRent.dto;

import kr.stylerent.StyleRent.dto.ProductResponse.ProductDataResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtherUserResponse {
    private Integer userId;
    private String username;
    private byte[] profileImage;
    private Double rankAverage;
    private List<ProductDataResponse> products;
}
