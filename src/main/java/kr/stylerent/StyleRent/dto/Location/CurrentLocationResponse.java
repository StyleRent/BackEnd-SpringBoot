package kr.stylerent.StyleRent.dto.Location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentLocationResponse {
    private String namelocation;
    private String latitude;
    private String longitude;
    private String error;
}
