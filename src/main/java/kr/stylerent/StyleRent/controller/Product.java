package kr.stylerent.StyleRent.controller;

import kr.stylerent.StyleRent.dto.ProductResponse.NewProductResponse;
import kr.stylerent.StyleRent.dto.RankDto;
import kr.stylerent.StyleRent.repository.ProductInformation;
import kr.stylerent.StyleRent.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class Product {

    private final ProductService productService;

//    @PostMapping("/api/v1/product/productinformation")
//    public ResponseEntity<ProductInformationDto> setInformation(
//            @RequestBody ProductInformationDto request
//    ) {
//        return ResponseEntity.ok(rankService.setRank(request));
//    }

    // 옷창 정의
    @PostMapping("/api/v1/product/newproduct")
    public ResponseEntity<NewProductResponse> setInformation(
    ) {
        return ResponseEntity.ok(productService.newProduct());
    }
}
