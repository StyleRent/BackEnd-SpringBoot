package kr.stylerent.StyleRent.dto.Location;


import kr.stylerent.StyleRent.dto.ProductResponse.ProductDataResponse;
import kr.stylerent.StyleRent.entity.Product;
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
public class NearbyUsersResponse {
    private String userName;
    private Integer userId;
    private byte[] profileImage;
    private String longtitude;
    private String latitude;
    private String distance;
    private List<ProductDataResponse> products;
}
