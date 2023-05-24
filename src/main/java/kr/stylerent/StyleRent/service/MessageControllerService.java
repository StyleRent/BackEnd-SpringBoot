package kr.stylerent.StyleRent.service;


import kr.stylerent.StyleRent.dto.MessageRequest.MessageInit;
import kr.stylerent.StyleRent.dto.MessageResponse.MessageInitResponse;
import kr.stylerent.StyleRent.entity.MessageController;
import kr.stylerent.StyleRent.entity.Product;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageControllerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageControllerRepository messageControllerRepository;

    private final ProductRepository productRepository;

    private final ProductInformationRepository productInformationRepository;

    private final ProductImageRepository productImageRepository;

    public MessageInitResponse messageInit(MessageInit request){
        //1. 사용자 검색
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 송신자
        User sender = userRepository.findByEmail(authentication.getName()).orElseThrow();

        // 수신자
        User receiver = userRepository.findById(request.getReceiverid()).orElseThrow();

        // 옷장
        Product product = productRepository.findById(request.getProductid()).orElseThrow();

        MessageController messageController = MessageController.builder()
                .user(sender)
                .receiver(receiver)
                .product(product)
                .build();

        messageControllerRepository.save(messageController);

        return MessageInitResponse.builder()
                .message("메시지 정의되었습니다.!")
                .build();

    }
}
