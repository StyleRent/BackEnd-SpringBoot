package kr.stylerent.StyleRent.service;

import kr.stylerent.StyleRent.dto.Rent.RentAddDto;
import kr.stylerent.StyleRent.dto.Rent.RentAddResponse;
import kr.stylerent.StyleRent.dto.Rent.RentFinishDto;
import kr.stylerent.StyleRent.entity.Product;
import kr.stylerent.StyleRent.entity.Rent;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.repository.ProductRepository;
import kr.stylerent.StyleRent.repository.RentRepository;
import kr.stylerent.StyleRent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentService {

    @Autowired
    private UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RentRepository rentRepository;

    public RentAddResponse addRent(RentAddDto request){
        // current user data
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        // renter id
        Optional<User> renter = userRepository.findById(request.getRenterId());

        // product id
        Optional<Product> product = productRepository.findById(request.getProductId());

        return RentAddResponse.builder()
                .message(user.getUsername())
                .build();
//
//
//        // check if current product is not rent on current time
////        Optional<Rent> checkProductRent = rentRepository.checkRentStatus(user.getId(), request.getRenterId(), request.getProductId());
//
////        if(checkProductRent.isPresent()){
////            return RentAddResponse.builder()
////                    .error("This product is already rented")
////                    .build();
////        }
//
//        if(renter.isPresent() && product.isPresent()){
//            rentRepository.save(
//                    Rent.builder()
//                            .status(true)
//                            .renter(renter.get())
//                            .user(user)
//                            .product(product.get())
//                            .build()
//            );
//            return RentAddResponse.builder()
//                    .message("This product has been successfully rented")
//                    .build();
//        }else{
//            return RentAddResponse.builder()
//                    .error("Cant find product or renter id")
//                    .build();
//        }


    }

//    public RentAddResponse finishRent(RentFinishDto request){
//        // my user is renter
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
//
//        // check if current product is not rent on current time
//        Optional<Rent> checkProductRent = rentRepository.checkRentStatus(user.getId(), request.getRenterId(), request.getProductId());
//
//        if(checkProductRent.isPresent()){
//            checkProductRent.get().setReturnedTime(new Date());
//            checkProductRent.get().setStatus(false);
//            rentRepository.save(checkProductRent.get());
//
//            return RentAddResponse.builder()
//                    .message("Product successfully returned!")
//                    .build();
//        }
//
//        return RentAddResponse.builder()
//                .error("Not found current product")
//                .build();
//
//    }
}
