package kr.stylerent.StyleRent.dto.Location;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NearbyUsersResponse {
    private String userName;
    private String longtitude;
    private String latitude;
    private Double distance;
}
