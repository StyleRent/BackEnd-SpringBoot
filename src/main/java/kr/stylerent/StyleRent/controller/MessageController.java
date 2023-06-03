package kr.stylerent.StyleRent.controller;

import kr.stylerent.StyleRent.dto.Message.*;
import kr.stylerent.StyleRent.dto.MessageRequest.MessageInit;
import kr.stylerent.StyleRent.dto.ProductRequest.ProductImagePath;
import kr.stylerent.StyleRent.dto.ProductResponse.FavResponse;
import kr.stylerent.StyleRent.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MessageController {
    private final MessageService messageService;

    // Get my chat
    @GetMapping("/api/v1/chat")
    public ResponseEntity<List<MyChatResponse>> getMyChat() {
        return ResponseEntity.ok(messageService.getMyChat());
    }

    // Message init
    @PostMapping("/api/v1/chat/init")
    public ResponseEntity<MessageInitResponse> messageInit(@RequestBody MessageDto request) {
        return ResponseEntity.ok(messageService.messageInit(request));
    }

    // Message History
    @PostMapping("/api/v1/chat/history")
    public ResponseEntity<MessageHistory> messageHistory(@RequestBody ChatHistoryRequest request) {
        return ResponseEntity.ok(messageService.messageHistory(request));
    }

    // Message History
    @PostMapping("/api/v1/chat/sendmessage")
    public ResponseEntity<SendMessageResponse> sendMessage(@RequestBody ChatDto request) {
        return ResponseEntity.ok(messageService.sendMessage(request));
    }
}
