package kr.stylerent.StyleRent.service;


import kr.stylerent.StyleRent.dto.Message.MessageDto;
import kr.stylerent.StyleRent.dto.Message.MessageHistory;
import kr.stylerent.StyleRent.dto.Message.MessageInitResponse;
import kr.stylerent.StyleRent.dto.Message.MyChatResponse;
import kr.stylerent.StyleRent.entity.MessageInit;
import kr.stylerent.StyleRent.entity.Product;
import kr.stylerent.StyleRent.entity.ProfileImage;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.repository.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    private final ProductRepository productRepository;

    private final ProductInformationRepository productInformationRepository;

    private final ProductImageRepository productImageRepository;
    private final ProfileImageRepository profileImageRepository;


    public List<MyChatResponse> getMyChat(){
        // current user data
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        List<MessageInit> myChats = messageRepository.findMessage(user.getId(), user.getId());
        List<MyChatResponse> response = new ArrayList<>();
        for(MessageInit mI : myChats){

            // find user image
            Optional<ProfileImage> profileImage = profileImageRepository.findById(mI.getReceiver().getId());

            response.add(MyChatResponse.builder()
                            .receiverImage(profileImage.map(ProfileImage::getData).orElse(null))
                            .receiverName(mI.getReceiver().getUsername())
                            .senderId(user.getId())
                            .productId(mI.getProduct().getProductid())
                            .receiverId(mI.getReceiver().getId())
                            .messageId(mI.getMessage_id())
                    .build());
        }

        return response;
    }

    public MessageInitResponse messageInit(MessageDto request){
        //1. 사용자 검색
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 송신자
        User sender = userRepository.findByEmail(authentication.getName()).orElseThrow();

        // 수신자
        Optional<User> receiver = userRepository.findById(request.getReceiverId());

        // 옷장
        Optional<Product> product = productRepository.findById(request.getProductId());

        if(receiver.isEmpty() || product.isEmpty()){
            return MessageInitResponse.builder()
                    .error("Error Cannot find receiver user!")
                    .build();
        }
        // check send history
        Optional<MessageInit> messageInit = messageRepository.checkMessageHistory(sender.getId(), receiver.get().getId(), product.get().getProductid());


        if(messageInit.isPresent()){
            return MessageInitResponse.builder()
                    .messageId(messageInit.get().getMessage_id())
                    .build();
        }else{
            MessageInit messagNew = MessageInit.builder()
                    .product(product.get())
                    .user(sender)
                    .receiver(receiver.get())
                    .build();

            messageRepository.save(messagNew);
            return MessageInitResponse.builder()
                    .messageId(messagNew.getMessage_id())
                    .message("메시지 정의되었습니다")
                    .build();
        }
    }


    // Message History
    public MessageHistory messageHistory(MessageDto request){
        //1. 사용자 검색
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 송신자
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        // 수신자
        Optional<User> receiver = userRepository.findById(request.getReceiverId());

        // 옷장
        Optional<Product> product = productRepository.findById(request.getProductId());

        if(receiver.isPresent() && product.isPresent()) {
//            Optional<MessageInit> messageInit = messageRepository.findMessage()/
        }else{

        }
        return MessageHistory.builder().build();

    }


}
