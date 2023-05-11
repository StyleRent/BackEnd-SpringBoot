package kr.stylerent.StyleRent.service;

import kr.stylerent.StyleRent.dto.ProductRequest.ProductInformationDto;
import kr.stylerent.StyleRent.dto.ProductResponse.NewProductResponse;
import kr.stylerent.StyleRent.dto.ProductResponse.ProductImageResponse;
import kr.stylerent.StyleRent.dto.ProductResponse.ProductInformationResponse;
import kr.stylerent.StyleRent.entity.Product;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductImage;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductInformation;
import kr.stylerent.StyleRent.entity.ProfileImage;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.repository.ProductImageRepository;
import kr.stylerent.StyleRent.repository.ProductInformationRepository;
import kr.stylerent.StyleRent.repository.ProductRepository;
import kr.stylerent.StyleRent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private UserRepository userRepository;

    private final ProductRepository productRepository;

    private final ProductInformationRepository productInformationRepository;

    private final ProductImageRepository productImageRepository;

    public NewProductResponse newProduct(){
        //1. 사용자 검색
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        Product currentProduct = Product.builder()
                .user(user)
                .build();


        Product saved = productRepository.save(currentProduct);

        return NewProductResponse.builder()
                .message("옷장 정의되었습니다!")
                .currentProductId(saved.getProductid())
                .build();
    }

    // 옷장 정보 추가
    public ProductInformationResponse newProductInformation(ProductInformationDto productInformationDto){
        // 정의된 옷장 데이터 반환
        Product initProduct = productRepository.findById(productInformationDto.getProductId()).orElseThrow();

        // 데이터 유효성 검사
        if (productInformationDto.getProductName().equals("") || productInformationDto.getProductPrice() == null) {
            return ProductInformationResponse.builder()
                    .error("제목과 가격을 모두 입력해주세요.")
                    .build();
        }


        ProductInformation productInformation = ProductInformation.builder()
                .product(initProduct)
                .name(productInformationDto.getProductName())
                .description(productInformationDto.getProductDescription())
                .build();



        productInformationRepository.save(productInformation);
        return ProductInformationResponse.builder()
                .productId(initProduct.getProductid())
                .message("옷장 정보 추가되었습니다")
                .build();
    }

    // 옷장 이미지 추가
//    public ProductImageResponse newProductImage(MultipartFile image) throws IOException {
//        try {
//            // Get Image name
//            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
//            Path imagePath = Paths.get("/path/to/image/folder");
//            if (!Files.exists(imagePath)) {
//                Files.createDirectories(imagePath);
//            }
//
//            // Image format
//            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
//            String newFilename = fileName + "." + extension;
//            Path newFilePath = imagePath.resolve(newFilename);
//
//            Product currentProduct = productRepository.findById(1).orElseThrow();
//            ProductImage currentImage = ProductImage.builder().image_path(newFilePath.toString()).product(currentProduct).build();
//
//            ProductImage save = productImageRepository.save(currentImage);
//
//            image.transferTo(newFilePath);
//            return ProductImageResponse.builder()
//                    .path(newFilePath.toString())
//                    .message("옷 사진 추가되었습니다~")
//                    .build();
//        }catch (IOException e){
//            return ProductImageResponse.builder()
//                    .error("사진추가할 수 없습니다")
//                    .build();
//        }
//    }
}
