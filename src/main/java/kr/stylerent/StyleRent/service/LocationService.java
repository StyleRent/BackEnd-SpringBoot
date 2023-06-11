package kr.stylerent.StyleRent.service;

import kr.stylerent.StyleRent.dto.Location.*;
import kr.stylerent.StyleRent.dto.ProductResponse.ProductDataResponse;
import kr.stylerent.StyleRent.dto.ProductResponse.ProductImageResponse;
import kr.stylerent.StyleRent.entity.*;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductImage;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductInformation;
import kr.stylerent.StyleRent.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private FavRepository favRepository;

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Autowired
    private ProductInformationRepository productInformationRepository;
    final double radius = 6371e3; // Earth's radius in meters

    // 위치 추가 기능
    public SetLocationResponse setLocation(LocationDto request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 해당 Token을 통해 사용자의 데이터 검색
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        if(request.getNamelocation().isEmpty()){
            return SetLocationResponse.builder().error("위치 이름 추가하세요!").build();
        }
        if(request.getLongitude().isEmpty() || request.getLatitude().isEmpty()){
            return SetLocationResponse.builder().error("Location Error!").build();
        }

        Location currentLocation = Location.builder()
                .locationName(request.getNamelocation())
                .user(user)
                .latitude(Double.parseDouble(request.getLatitude()))
                .longitude(Double.parseDouble(request.getLongitude()))
                .build();
        locationRepository.save(currentLocation);
        return SetLocationResponse.builder()
                .namelocation(currentLocation.getLocationName())
                .longitude(currentLocation.getLongitude().toString())
                .latitude(currentLocation.getLatitude().toString())
                .build();
    }

    // 위치 수정 기능
    public SetLocationResponse updateLocation(LocationDto request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 해당 Token을 통해 사용자의 데이터 검색
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        Location currentLocation = locationRepository.findById(user.getId()).orElseThrow();

        currentLocation.locationUpdate(request);

        locationRepository.save(currentLocation);

        return SetLocationResponse.builder()
                .namelocation(currentLocation.getLocationName())
                .longitude(currentLocation.getLongitude().toString())
                .latitude(currentLocation.getLatitude().toString())
                .build();
    }

    public CurrentLocationResponse getCurrentLocation(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 해당 Token을 통해 사용자의 데이터 검색
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        Optional<Location> currentLocation = locationRepository.findById(user.getId());
        if(currentLocation.isPresent()){
            return CurrentLocationResponse.builder()
                    .longitude(currentLocation.get().getLongitude().toString())
                    .latitude(currentLocation.get().getLatitude().toString())
                    .build();
        }else{
            return CurrentLocationResponse.builder().error("Please add current location").build();
        }
    }
    
    
    // Get Nearby Users data
    public List<NearbyUsersResponse> getNearbyUsers(Integer distance){
        // 해당 사용자의 데이터
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        
        // 자신의 위치
        Location myLocation = locationRepository.findCurrentLocaitonByUserId(user.getId());
        
        List<User> allUsers = userRepository.findAll();
        List<NearbyUsersResponse> nearByUsers = new ArrayList<>();

        
        for(User currentUser : allUsers){
            // 사용자의 위치 받기
            Optional<Location> optionalLocation = locationRepository.findById(currentUser.getId());
            if (optionalLocation.isEmpty()) {
                // Handle the case where the location is empty for the current user
                continue;
            }else{
                // 사용자의 위치 받기
                Location currentLocation = optionalLocation.get();

                double dLat = Math.toRadians(currentLocation.getLatitude() - myLocation.getLatitude());
                double dLon = Math.toRadians(currentLocation.getLongitude() - myLocation.getLongitude());
                double lat1 = Math.toRadians(myLocation.getLatitude());
                double lat2 = Math.toRadians(currentLocation.getLatitude());

                double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                double dist = radius * c;
                double distanceD = distance;

                // Check length of products current user
                List<Product> products = productRepository.findAllById(currentUser.getId());
                System.out.println("Currnet user ---------------->>>>" + currentUser.getUsername());
                Optional<ProfileImage> profileImage = profileImageRepository.findById(currentUser.getId());

                if (dist <= distanceD && products.size() >= 1 && !user.getId().equals(currentUser.getId())) {
                    nearByUsers.add(NearbyUsersResponse.builder()
                            .distance(Double.toString(dist))
                            .userName(currentUser.getUsername())
                            .userId(currentUser.getId())
                                    .profileImage(profileImage.map(ProfileImage::getData).orElse(null))
                            .longtitude(currentLocation.getLongitude().toString())
                            .latitude(currentLocation.getLatitude().toString())
                            .products(generatorProductList(currentUser, user))
                            .build());
                }
            }
        }
        return nearByUsers;

    }

    private List<ProductDataResponse> generatorProductList(User currentUser, User myData){
        // get current user location
        List<Product> products = productRepository.findAllById(currentUser.getId());
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


            // check liked product
            Optional<Fav> checkIsLiked = favRepository.checkCurrentLike(myData.getId(), p.getProductid());
            if(checkIsLiked.isPresent()){
                liked = true;
            }

            ProductInformation productInformation = productInformationRepository.findInfoById(p.getProductid());
            if(productInformation != null){
                productDataResponses.add(ProductDataResponse.builder()
                        .productId(p.getProductid())
                        .productName(productInformation.getName())
                        .productInfo(productInformation.getDescription())
                        .productPrice(productInformation.getPrice())
                        .liked(liked)
                        .rentStatus(rentStatus)
                        .imagePath(productImageResponses)
                        .build());
            }

        }
        return productDataResponses;
    }
}
