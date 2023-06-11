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
    private byte[] receiverImage; // String -> set image from base64
    private Integer senderId;
    private Boolean myChat;
    private Boolean rentStatus;
    private Boolean isRentedToMe;
    private Integer receiverId;
    private String productName;
    private String productPrice;
    private String receiverName;
    private String productImage; // getImage by path
    private String lastMessage;
    private Integer productId;
}
