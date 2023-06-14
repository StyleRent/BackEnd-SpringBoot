package kr.stylerent.StyleRent.dto;

import kr.stylerent.StyleRent.entity.Rank;
import kr.stylerent.StyleRent.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankDto {
    private Integer rank;
    private Integer receiverId;
    private Integer productId;
    private String evaluationText;
}