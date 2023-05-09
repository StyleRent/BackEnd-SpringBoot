package kr.stylerent.StyleRent.service;

import kr.stylerent.StyleRent.dto.ProductResponse.NewProductResponse;
import kr.stylerent.StyleRent.entity.Product.Product;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.entity.UserData;
import kr.stylerent.StyleRent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private UserRepository userRepository;

    public NewProductResponse newProduct(){
        //1. 사용자 검색
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        Product currentProduct = Product.builder()
                .user(user)
                .build();



        return NewProductResponse.builder()
                .message("옷장 정의되었습니다!")
                .build();
    }
}
