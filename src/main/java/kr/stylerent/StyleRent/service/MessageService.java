package kr.stylerent.StyleRent.service;


import kr.stylerent.StyleRent.dto.Message.*;
import kr.stylerent.StyleRent.entity.*;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductImage;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductInformation;
import kr.stylerent.StyleRent.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Autowired
    private UserRepository userRepository;

    private final MessageRepository messageRepository;

    private final RentRepository rentRepository;

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
            Boolean rentStatus = false;

            // check rent status ->
            Optional<Rent> checkProductRent = rentRepository.checkRentStatusByProductId(mI.getProduct().getProductid());

            if(checkProductRent.isPresent()){
                rentStatus = true;
            }


            ProductInformation productInformation = productInformationRepository.findInfoById(mI.getProduct().getProductid());



            if(mI.getReceiver().getId().equals(user.getId())){
                // find user image
                Optional<ProfileImage> profileImage = profileImageRepository.findById(mI.getUser().getId());
                List<ProductImage> productImage = productImageRepository.findAllImagesByProductId(mI.getProduct().getProductid());


                try {
                    File imageFile = new File(productImage.get(0).getImage_path());
                    byte[] imageBytes = FileUtils.readFileToByteArray(imageFile);
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                    // Use the base64Image as needed (e.g., store in a database, return in a response, etc.)


                    response.add(MyChatResponse.builder()
                        .receiverImage(profileImage.map(ProfileImage::getData).orElse(null))
                        .receiverName(mI.getUser().getUsername())
                        .senderId(user.getId())
                        .myChat(true)
                        .isRentedToMe(false)
                        .productName(productInformation.getName())
                        .productPrice(productInformation.getPrice())
                        .rentStatus(rentStatus)
                        .productId(mI.getProduct().getProductid())
                        .productImage(base64Image)
                        .receiverId(mI.getUser().getId())
                        .messageId(mI.getMessage_id())
                        .build());
                } catch (IOException e) {
                    // Handle file read or encoding errors
                }
            }else{
                Boolean rentedToMe = false;
                // find user image
                Optional<ProfileImage> profileImage = profileImageRepository.findById(mI.getReceiver().getId());
                List<ProductImage> productImage = productImageRepository.findAllImagesByProductId(mI.getProduct().getProductid());
                Optional<Rent> checkRentToMe = rentRepository.checkRentStatus(mI.getReceiver().getId() ,user.getId(), mI.getProduct().getProductid());

                if(checkRentToMe.isPresent()){
                    rentedToMe = true;
                }

                try {
                    File imageFile = new File(productImage.get(0).getImage_path());
                    byte[] imageBytes = FileUtils.readFileToByteArray(imageFile);
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                    // Use the base64Image as needed (e.g., store in a database, return in a response, etc.)

                    response.add(MyChatResponse.builder()
                            .receiverImage(profileImage.map(ProfileImage::getData).orElse(null))
                            .receiverName(mI.getReceiver().getUsername())
                            .senderId(user.getId())
                            .myChat(false)
                            .productName(productInformation.getName())
                            .productPrice(productInformation.getPrice())
                            .rentStatus(rentStatus)
                            .isRentedToMe(rentedToMe)
                            .productId(mI.getProduct().getProductid())
                            .productImage(base64Image)
                            .receiverId(mI.getReceiver().getId())
                            .messageId(mI.getMessage_id())
                            .build());
                } catch (IOException e) {
                    // Handle file read or encoding errors
                }
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
        Optional<MessageInit> mI = messageRepository.checkMessage(sender.getId(), product.get().getProductid());


        // chat is not empty
        if(mI.isPresent()){

            Boolean rentStatus = false;
            Boolean rentedToMe = false;

            // check rent status ->
            Optional<Rent> checkProductRent = rentRepository.checkRentStatusByProductId(request.getProductId());

            if(checkProductRent.isPresent()){
                rentStatus = true;
            }


            ProductInformation productInformation = productInformationRepository.findInfoById(request.getProductId());

            List<ProductImage> productImage = productImageRepository.findAllImagesByProductId(request.getProductId());


            Optional<Rent> checkRentToMe = rentRepository.checkRentStatus(request.getReceiverId(), sender.getId(), request.getProductId());

            if(checkRentToMe.isPresent()){
                rentedToMe = true;
            }

            try {
                File imageFile = new File(productImage.get(0).getImage_path());
                byte[] imageBytes = FileUtils.readFileToByteArray(imageFile);
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);


                return MessageInitResponse.builder()
                    .messageId(mI.get().getMessage_id())
                        .myChat(false)
                        .userId(sender.getId())
                        .receiverId(request.getReceiverId())
                        .rentStatus(rentStatus)
                        .productImage(base64Image)
                        .isRentedToMe(rentedToMe)
                        .productId(request.getProductId())
                        .productName(productInformation.getName())
                        .productPrice(productInformation.getPrice())
                    .build();

            } catch (IOException e) {
                // Handle file read or encoding errors
                return MessageInitResponse.builder()
                        .error("Cant find product Image")
                        .build();
            }

        }else{


            MessageInit mInew = MessageInit.builder()
                    .product(product.get())
                    .user(sender)
                    .receiver(receiver.get())
                    .build();

            MessageInit getM = messageRepository.save(mInew);



            // add datas
            Boolean rentStatus = false;
            Boolean rentedToMe = false;

            // check rent status ->
            Optional<Rent> checkProductRent = rentRepository.checkRentStatusByProductId(request.getProductId());

            if(checkProductRent.isPresent()){
                rentStatus = true;
            }


            ProductInformation productInformation = productInformationRepository.findInfoById(request.getProductId());

            List<ProductImage> productImage = productImageRepository.findAllImagesByProductId(request.getProductId());


            Optional<Rent> checkRentToMe = rentRepository.checkRentStatus(request.getReceiverId(), sender.getId(), request.getProductId());

            if(checkRentToMe.isPresent()){
                rentedToMe = true;
            }

            try {
                File imageFile = new File(productImage.get(0).getImage_path());
                byte[] imageBytes = FileUtils.readFileToByteArray(imageFile);
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                return MessageInitResponse.builder()
                        .messageId(getM.getMessage_id())
                        .myChat(false)
                        .userId(sender.getId())
                        .receiverId(request.getReceiverId())
                        .rentStatus(rentStatus)
                        .productImage(base64Image)
                        .productId(request.getProductId())
                        .isRentedToMe(rentedToMe)
                        .productName(productInformation.getName())
                        .productPrice(productInformation.getPrice())
                        .build();

            } catch (IOException e) {
                // Handle file read or encoding errors
            }





            return MessageInitResponse.builder()
                    .messageId(getM.getMessage_id())
                    .myChat(false)
                    .rentStatus(rentStatus)
                    .isRentedToMe(rentedToMe)
                    .productId(request.getProductId())
                    .productName(productInformation.getName())
                    .productPrice(productInformation.getPrice())
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
