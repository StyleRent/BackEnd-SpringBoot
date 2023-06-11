package kr.stylerent.StyleRent.dto.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageInitResponse {

    private Integer messageId;
    private String message;
    private Integer productId;
    private String productName;
    private String productPrice;
    private Integer userId;
    private Integer receiverId;
    private Boolean rentStatus;
    private Boolean myChat;
    private Boolean isRentedToMe;
    private String productImage;
    private String error;
}
