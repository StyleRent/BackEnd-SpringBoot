package kr.stylerent.StyleRent.controller;

import kr.stylerent.StyleRent.dto.ProductInformationDto;
import kr.stylerent.StyleRent.dto.RankDto;
import kr.stylerent.StyleRent.repository.ProductInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class Product {


    @PostMapping("/api/v1/product/newproduct")
    public ResponseEntity<ProductInformationDto> setInformation(
            @RequestBody ProductInformationDto request
    ) {
        return ResponseEntity.ok(rankService.setRank(request));
    }
}
