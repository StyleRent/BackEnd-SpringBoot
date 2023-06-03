package kr.stylerent.StyleRent.dto.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    private String chat;
    private Integer messageId;
    private Integer receiverId;
    private Integer productId;
}
