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
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RentRepository rentRepository;

    public RentAddResponse addRent(RentAddDto request){
        // current user data
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        // renter id
        Optional<User> renter = userRepository.findById(request.getRenterId());

        // product id
        Optional<Product> product = productRepository.findById(request.getProductId());

        if(renter.isEmpty() && product.isEmpty()){
            return RentAddResponse.builder()
                    .error("Error find user or product")
                    .build();
        }

        // check if current product is not rent on current time
        Optional<Rent> checkProductRent = rentRepository.checkRentStatus(user.getId(), request.getRenterId(), request.getProductId());

        if(checkProductRent.isPresent()){
            return RentAddResponse.builder()
                    .error("This product is already rented")
                    .build();
        }

        rentRepository.save(
                    Rent.builder()
                            .status(true)
                            .renter(renter.get())
                            .user(user)
                            .product(product.get())
                            .build());

        return RentAddResponse.builder()
                .message("This product has been successfully rented")
                .build();
    }

    public RentAddResponse finishRent(RentFinishDto request){
        // current user data
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        // renter id
        Optional<User> renter = userRepository.findById(request.getRenterId());

        // product id
        Optional<Product> product = productRepository.findById(request.getProductId());

        if(renter.isEmpty() && product.isEmpty()){
            return RentAddResponse.builder()
                    .error("Error find user or product")
                    .build();
        }

        // check if current product is not rent on current time
        Optional<Rent> checkProductRent = rentRepository.checkRentStatus(user.getId(), request.getRenterId(), request.getProductId());

        if(checkProductRent.isEmpty()){
            return RentAddResponse.builder()
                    .error("This product has not been rented yet")
                    .build();
        }

        Rent update = checkProductRent.get();
        // update data
        update.setStatus(false);
        update.setReturnedTime(new Date());

        rentRepository.save(update);

        return RentAddResponse.builder()
                .message("This product has been successfully returned")
                .build();
    }
}
