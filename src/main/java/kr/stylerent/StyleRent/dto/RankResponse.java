package kr.stylerent.StyleRent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankResponse {
    private Integer id;
    private Integer rank;
    private Integer userid;
    private Integer recieverid;

    private String error;
}
