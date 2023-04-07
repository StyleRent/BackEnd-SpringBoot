package kr.stylerent.StyleRent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoordinateResponse {
    private String coord_long;
    private String coord_lat;
}
