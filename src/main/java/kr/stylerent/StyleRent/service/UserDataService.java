package kr.stylerent.StyleRent.service;

import kr.stylerent.StyleRent.dto.*;
import kr.stylerent.StyleRent.dto.ProductResponse.ProductDataResponse;
import kr.stylerent.StyleRent.dto.ProductResponse.ProductImageResponse;
import kr.stylerent.StyleRent.entity.*;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductImage;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductInformation;
import kr.stylerent.StyleRent.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final UserDataRepository userDataRepository;

    private final ProfileImageRepository profileImageRepository;

    private final RankRepository rankRepository;

    private final UserRepository userRepository;
    private final RentRepository rentRepository;

    private final ProductRepository productRepository;
    private final ProductInformationRepository productInformationRepository;
    private final ProductImageRepository productImageRepository;


    public OtherUserResponse otherUserResponse(Integer userId){
        User user = userRepository.findById(userId).orElseThrow();
        Optional<ProfileImage> profileImage = profileImageRepository.findById(user.getId());


        // rank average
        List<Rank> rankList = rankRepository.findAllByReceiverId(user.getId());
        double roundedAverage = 4.5;
        int sum = 0;
        int count = rankList.size();

        for (Rank rank : rankList) {
            sum += rank.getRank();
        }

        double average = count > 0 ? (double) sum / count : 0.0;
        roundedAverage = Math.round(average * 10.0) / 10.0;

        // get current user location
        List<Product> products = productRepository.findAllById(user.getId());
        List<ProductDataResponse> productDataResponses = new ArrayList<>();

        for(Product p : products){
            Boolean rentStatus = false;
            Boolean liked = false;

            // check rent status ->
            Optional<Rent> checkProductRent = rentRepository.checkRentStatusByProductId(p.getProductid());
            if(checkProductRent.isPresent()){
                rentStatus = true;
            }


            List<ProductImage> productImages = productImageRepository.findAllImagesByProductId(p.getProductid());
            List<ProductImageResponse> productImageResponses = new ArrayList<>(); // Create a new list for each product

            for (ProductImage pi : productImages) {
                productImageResponses.add(ProductImageResponse.builder()
                        .path(pi.getImage_path())
                        .build());
            }


            ProductInformation productInformation = productInformationRepository.findInfoById(p.getProductid());
            if(productInformation != null){
                productDataResponses.add(ProductDataResponse.builder()
                        .productId(p.getProductid())
                        .productName(productInformation.getName())
                        .productInfo(productInformation.getDescription())
                        .productPrice(productInformation.getPrice())
                        .liked(liked)
                        .rankAverage(roundedAverage)
                        .rentStatus(rentStatus)
                        .imagePath(productImageResponses)
                        .build());
            }

        }

        return OtherUserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .rankAverage(roundedAverage)
                .profileImage(profileImage.map(ProfileImage::getData).orElse(null))
                .products(productDataResponses)
                .build();

    }

    public Integer getRankAverage(List<RankResponse> rankResponses){
        Integer num = rankResponses.size();
        Integer sum = 0;
        if(!rankResponses.isEmpty()){
            for(RankResponse rR : rankResponses){
                sum += rR.getRank();
            }
            return sum / rankResponses.size();
        }
        return 0;
    }


    public UserDataResponse getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        UserData userData = userDataRepository.findById(user.getId()).orElse(new UserData());
        List<ProductDataResponse> productDatas = new ArrayList<>();

        // 자신만의 옷장 검색
        List<ProductDataResponse> products = generatorProductList(user);





        // Find All data by marked rank
        List<RankResponse> marks = null;
        marks = rankRepository.findAllById(user.getId())
                .stream()
                .map(ranks -> RankResponse.builder()
                        .id(ranks.getId())
                        .rank(ranks.getRank())
                        .userid(ranks.getUser().getId())
                        .recieverid(ranks.getReceiver().getId())
                        .build()
                )
                .collect(Collectors.toList());


        // find all by received id
        List<RankResponse> receivedRank = null;
        receivedRank = rankRepository.findAllByReceiverId(user.getId())
                .stream()
                .map(ranks -> RankResponse.builder()
                        .id(ranks.getId())
                        .rank(ranks.getRank())
                        .userid(ranks.getUser().getId())
                        .recieverid(ranks.getReceiver().getId())
                        .build()
                )
                .collect(Collectors.toList());

        // Calculate average Rank
        Integer sum = getRankAverage(receivedRank);


        ProfileImage profileImage = profileImageRepository.findById(user.getId()).orElse(new ProfileImage());
        ImageResponse imageResponse = ImageResponse.builder().imageByte(profileImage.getData()).build();


        return UserDataResponse.builder()
                .userid(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phonenumber(userData.getPhonenumber())
                .averageRank(sum)
                .marks(marks)
                .receivedRank(receivedRank)
                .imageResponse(imageResponse)
                .products(products)
                .build();
    }


    private List<ProductDataResponse> generatorProductList(User currentUser){
        // get current user location
        List<Product> products = productRepository.findAllById(currentUser.getId());
        List<ProductDataResponse> productDataResponses = new ArrayList<>();
        List<ProductImageResponse> productImageResponses = new ArrayList<>();

        for(Product p : products){
            Boolean rentStatus = false;

            // check rent status ->
            Optional<Rent> checkProductRent = rentRepository.checkRentStatusByProductId(p.getProductid());
            if(checkProductRent.isPresent()){
                rentStatus = true;
            }
            List<ProductImage> productImage = productImageRepository.findAllImagesByProductId(p.getProductid());
            for(ProductImage pi : productImage){
                productImageResponses.add(ProductImageResponse.builder()
                        .path(pi.getImage_path())
                        .build());
            }
            ProductInformation productInformation = productInformationRepository.findInfoById(p.getProductid());
            if(productInformation != null){
                productDataResponses.add(ProductDataResponse.builder()
                        .productId(p.getProductid())
                        .productName(productInformation.getName())
                        .productInfo(productInformation.getDescription())
                                .rentStatus(rentStatus)
                        .productPrice(productInformation.getPrice())
                        .imagePath(productImageResponses)
                        .build());
            }
        }
        return productDataResponses;
    }

    public UserDataUpdateResponseMessage updateUserData(UserDataDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        if(request.getPhonenumber() != null){
            UserData updateData = userDataRepository.findById(user.getId()).orElse(new UserData());
            updateData.updatePhoneNumber(request.getPhonenumber(), user);

            userDataRepository.save(updateData);
        }

        return(UserDataUpdateResponseMessage.builder().message("Data updated!").build());
    }
}
