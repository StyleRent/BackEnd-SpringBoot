package kr.stylerent.StyleRent.dto.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SenderHistory {
    private Integer chatId;
    private Integer userId;
    private String message;
}
