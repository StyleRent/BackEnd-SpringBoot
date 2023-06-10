package kr.stylerent.StyleRent.controller;

import kr.stylerent.StyleRent.dto.ProductRequest.ProductInformationDto;
import kr.stylerent.StyleRent.dto.ProductResponse.ProductInformationResponse;
import kr.stylerent.StyleRent.dto.Rent.RentAddDto;
import kr.stylerent.StyleRent.dto.Rent.RentAddResponse;
import kr.stylerent.StyleRent.dto.Rent.RentFinishDto;
import kr.stylerent.StyleRent.repository.UserRepository;
import kr.stylerent.StyleRent.service.ProductService;
import kr.stylerent.StyleRent.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class RentController {

     private final RentService rentService;
    // Add rent
    @PostMapping("/api/v1/rent/createrent")
    public ResponseEntity<RentAddResponse> createRent(
            @RequestBody RentAddDto request
    ) {
        return ResponseEntity.ok(rentService.addRent(request));
    }

    // return rent
//    @PostMapping("/api/v1/rent/return")
//    public ResponseEntity<RentAddResponse> addRent(
//            @RequestBody RentFinishDto request
//    ) {
//        return ResponseEntity.ok(rentService.finishRent(request));
//    }


}
