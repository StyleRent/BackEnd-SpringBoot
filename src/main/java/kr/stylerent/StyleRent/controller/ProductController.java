package kr.stylerent.StyleRent.controller;

import kr.stylerent.StyleRent.dto.ProductRequest.ProductInformationDto;
import kr.stylerent.StyleRent.dto.ProductResponse.NewProductResponse;
import kr.stylerent.StyleRent.dto.ProductResponse.ProductImageResponse;
import kr.stylerent.StyleRent.dto.ProductResponse.ProductInformationResponse;
import kr.stylerent.StyleRent.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ProductController {


    private final ProductService productService;



//    @PostMapping("/api/v1/product/productinformation")
//    public ResponseEntity<ProductInformationDto> setInformation(
//            @RequestBody ProductInformationDto request
//    ) {
//        return ResponseEntity.ok(rankService.setRank(request));
//    }

     // 옷창 정의
    @PostMapping("/api/v1/product/newproduct")
    public ResponseEntity<NewProductResponse> initProduct(
    ) {
        return ResponseEntity.ok(productService.newProduct());
    }

    // 옷자 정보 추가
    @PostMapping("/api/v1/product/addproductinfo")
    public ResponseEntity<ProductInformationResponse> setProductInformation(
            @RequestBody ProductInformationDto request
    ) {
        return ResponseEntity.ok(productService.newProductInformation(request));
    }


    // Add product Image
//    @PostMapping("/api/v1/product/addproductimage")
//    public ResponseEntity<ProductImageResponse> setProductImage(
//            @RequestParam("image") MultipartFile request
//    ) throws IOException {
//        return ResponseEntity.ok(productService.newProductImage(request));
//    }
}
