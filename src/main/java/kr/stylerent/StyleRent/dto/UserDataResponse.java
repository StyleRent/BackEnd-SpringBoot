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
public class UserDataResponse {
    private Integer userid;
    private String username;
    private String email;
    private String phonenumber;
    private Integer averageRank;
    private List<RankResponse> receivedRank;
    private List<RankResponse> marks;
    private CoordinateResponse coordinateResponse;
    private ImageResponse imageResponse;
    private List<ProductDataResponse> products;
}
