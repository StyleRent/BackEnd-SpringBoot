package kr.stylerent.StyleRent.dto.Rent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentFinishDto {
    Integer renterId;
    Integer productId;
}
