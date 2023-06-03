package kr.stylerent.StyleRent.service;


import kr.stylerent.StyleRent.dto.Message.*;
import kr.stylerent.StyleRent.entity.*;
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

    private final MessageRepository messageRepository;


    private final ChattingRepository chattingRepository;

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

            if(mI.getReceiver().getId().equals(user.getId())){
                // find user image
                Optional<ProfileImage> profileImage = profileImageRepository.findById(mI.getUser().getId());

                response.add(MyChatResponse.builder()
                        .receiverImage(profileImage.map(ProfileImage::getData).orElse(null))
                        .receiverName(mI.getUser().getUsername())
                        .senderId(user.getId())
                        .productId(mI.getProduct().getProductid())
                        .receiverId(mI.getUser().getId())
                        .messageId(mI.getMessage_id())
                        .build());
            }else{
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
        }

        return response;
    }



    // 메시지 생성
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
        Optional<MessageInit> messageInit = messageRepository.checkMessage(sender.getId(), product.get().getProductid());


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
    public MessageHistory messageHistory(ChatHistoryRequest request){
        //1. 사용자 검색
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 송신자
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        Optional<MessageInit> messageInit = messageRepository.findById(request.getMessageId());
        if(messageInit.isPresent()){
            System.out.println("Message id found ------>>>>>>>>>>>  " + messageInit.get().getMessage_id());
            System.out.println("Message id found ------>>>>>>>>>>>  " + messageInit.get().getMessage_id());

            List<Chatting> chattingSenderList = chattingRepository.findAllBySenderId(messageInit.get().getMessage_id(), user.getId());
            List<Chatting> chattingReceiverList = chattingRepository.findAllBySenderId(messageInit.get().getMessage_id(), request.getReceiverId());


            // Init sender and receiver chat
            List<SenderHistory> senderHistories = new ArrayList<>();
            List<ReceiverHistory> receiverHistories = new ArrayList<>();

            // sender
            for(Chatting ch : chattingSenderList){
                senderHistories.add(SenderHistory.builder()
                                .userId(user.getId())
                                .message(ch.getMessage())
                                .chatId(ch.getChat_id())
                        .build());
            }

            // receiver
            for(Chatting ch : chattingReceiverList){
                receiverHistories.add(ReceiverHistory.builder()
                        .userId(user.getId())
                        .message(ch.getMessage())
                        .chatId(ch.getChat_id())
                        .build());
            }

            return MessageHistory.builder()
                    .messageId(messageInit.get().getMessage_id())
                    .sender(senderHistories)
                    .receiver(receiverHistories)
                    .build();


        }else{
            return MessageHistory.builder()
                    .error("Product or reciever data is null")
                    .build();
        }

    }

    // Send Message
    public SendMessageResponse sendMessage(ChatDto request){
        System.out.println("SEnd message method ---------------------");
        //1. 사용자 검색
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 송신자
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        // 수신자
        Optional<User> receiver = userRepository.findById(request.getReceiverId());
        // 옷장
        Optional<Product> product = productRepository.findById(request.getProductId());

        // messageInit
        Optional<MessageInit> messageInit = messageRepository.checkMessage(user.getId(), product.get().getProductid());

        System.out.println("Chat is ------>>" + messageInit.get().getMessage_id());
        if(messageInit.isPresent()) {
            //current message id
            System.out.println("Chat is ------>>" + messageInit.get().getMessage_id());
            Chatting chat = Chatting.builder()
                    .sender(user)
                    .messageInit(messageInit.get())
                    .message(request.getChat())
                    .build();
            chattingRepository.save(chat);
            return SendMessageResponse.builder().status("Message has been sent").build();

        } else{
            return SendMessageResponse.builder().error("Error cannot find data").build();
        }
    }
}
