package kr.stylerent.StyleRent.dto.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyChatResponse {
    private Integer messageId;
    private byte[] receiverImage;
    private Integer senderId;
    private Integer receiverId;
    private String receiverName;
    private String productImage;
    private String lastMessage;
    private Integer productId;
}
