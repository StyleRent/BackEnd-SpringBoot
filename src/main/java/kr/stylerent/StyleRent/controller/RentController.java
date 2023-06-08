package kr.stylerent.StyleRent.controller;

import kr.stylerent.StyleRent.dto.ProductRequest.ProductInformationDto;
import kr.stylerent.StyleRent.dto.ProductResponse.ProductInformationResponse;
import kr.stylerent.StyleRent.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class RentController {

    private final ProductService productService;
    // 옷자 정보 추가
    @PostMapping("/api/v1/rent/add")
    public ResponseEntity<ProductInformationResponse> addRent(
            @RequestBody ProductInformationDto request
    ) {
        return ResponseEntity.ok(productService.newProductInformation(request));
    }
}
