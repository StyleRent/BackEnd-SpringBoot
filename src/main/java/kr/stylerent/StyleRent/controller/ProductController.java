package kr.stylerent.StyleRent.controller;

import kr.stylerent.StyleRent.dto.Message.MyChatResponse;
import kr.stylerent.StyleRent.dto.ProductResponse.*;
import kr.stylerent.StyleRent.dto.ProductRequest.ProductImagePath;
import kr.stylerent.StyleRent.dto.ProductRequest.ProductInformationDto;
import org.springframework.beans.factory.annotation.Value;
import kr.stylerent.StyleRent.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {


    private final ProductService productService;

    @Value("${spring.web.resources.static-locations}")
    private String staticFileLocation;


    @PostMapping("/api/v1/product/newimage/{productId}")
    public ResponseEntity<ProductImageResponse> addImage(@PathVariable Integer productId, @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(productService.addImage(productId, file));
    }

    // API request관리
    @DeleteMapping("/api/v1/product/delete/{productId}")
    public ResponseEntity<ProductDeleteResponse> deleteProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(productService.productDelete(productId));
    }

//    @GetMapping("/api/v1/product/{productId}/image")
//    public ResponseEntity<Resource> getImage(@PathVariable Integer productId, @PathVariable String imageName) throws IOException {
//        String imagePath = staticFileLocation + "/products/" + productId + "/";
//
//        Path imageFilePath = Files.list(Path.of(imagePath))
//                .filter(Files::isRegularFile)
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Image not found"));
//
//        // Create a FileSystemResource from the downloaded image file
//        Resource imageResource = new FileSystemResource(imageFilePath);
//
//        // Set the appropriate headers for the image response
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(imageResource);
//    }

    @PostMapping("/api/v1/product/image")
    public ResponseEntity<Resource> getImage(@RequestBody ProductImagePath request) throws IOException {
        String imagePath = request.getImagePath();
        File imageFile = new File(imagePath);

        // Create a FileSystemResource from the downloaded image file
        Resource imageResource = new FileSystemResource(imageFile);

        // Set the appropriate headers for the image response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imageResource);
    }

    @GetMapping("/api/v1/product/{search}")
    public ResponseEntity<List<ProductDataResponse>> findProduct(@PathVariable("search") String search) {
        return ResponseEntity.ok(productService.findProduct(search));
    }

    @GetMapping("/api/v1/product/getproductimages/{productId}")
    public ResponseEntity<List<ProductImageResponse>> findProduct(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(productService.getProductImage(productId));
    }




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

    // 좋아요
    @PostMapping("/api/v1/product/like/{productId}")
    public ResponseEntity<FavResponse> addFavProduct(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(productService.addFavProduct(productId));
    }

    // 좋아요
    @GetMapping("/api/v1/product/like")
    public ResponseEntity<List<MyFavResponse>> getFavProduct() {
        return ResponseEntity.ok(productService.getFavProd());
    }


    // 좋아요 삭제
    @DeleteMapping("/api/v1/product/like/{productId}")
    public ResponseEntity<FavResponse> deleteFavProduct(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(productService.deleteFavProduct(productId));
    }
}
