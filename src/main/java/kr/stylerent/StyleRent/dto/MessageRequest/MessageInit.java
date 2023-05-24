package kr.stylerent.StyleRent.dto.MessageRequest;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageInit {
    private Integer productid; // 옷장 아이디
    private Integer senderid; // 송신자
    private Integer receiverid; // 수신자
}
