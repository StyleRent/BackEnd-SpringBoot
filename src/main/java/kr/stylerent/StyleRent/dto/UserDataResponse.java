package kr.stylerent.StyleRent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDataResponse {
    private Integer userid;
    private String username;
    private String email;
    private String phonenumber;
    private CoordinateResponse coordinateResponse;
    private ImageResponse imageResponse;
}
