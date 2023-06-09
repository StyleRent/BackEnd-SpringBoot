package kr.stylerent.StyleRent.dto.Message;

import kr.stylerent.StyleRent.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageHistory {
    private Integer messageId;
    private List<SenderHistory> sender;
    private List<ReceiverHistory> receiver;
    private String error;
}
