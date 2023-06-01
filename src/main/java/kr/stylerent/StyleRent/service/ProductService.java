package kr.stylerent.StyleRent.service;

import jakarta.transaction.Transactional;
import kr.stylerent.StyleRent.dto.ProductResponse.*;
import kr.stylerent.StyleRent.dto.ProductRequest.ProductInformationDto;
import kr.stylerent.StyleRent.entity.Fav;
import kr.stylerent.StyleRent.entity.Product;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductImage;
import kr.stylerent.StyleRent.entity.ProductEntity.ProductInformation;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Member;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.web.resources.static-locations}")
    private String staticFileLocation;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductInformationRepository productInformationRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    private final FavRepository favRepository;


    // 옷장 데이터 삭제
    public ProductDeleteResponse productDelete(Integer productId){
        // product info 제거
        productInformationRepository.deleteById(productId);

        // product image 제거
        List<ProductImage> productImages = productImageRepository.findAllImagesByProductId(productId);

        productImageRepository.deleteAll(productImages);

        productRepository.deleteById(productId);

        return ProductDeleteResponse.builder()
                .message("옷장 삭제되었습니다!")
                .build();
    }


    // Get Image
    public Resource getImage(Integer productId) throws IOException {
        String imagePath = staticFileLocation + "/products/" + productId + "/";

        Path imageFilePath = Files.list(Path.of(imagePath))
                .filter(Files::isRegularFile)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Image not found"));


        return new FileSystemResource(imageFilePath);
    }


    public ProductImageResponse addImage(Integer productId, MultipartFile file){

        Product product = productRepository.findById(productId).orElseThrow();

        if (file.isEmpty()) {
            return ProductImageResponse.builder()
                    .error("File is empty")
                    .build();
        }
        try {
            String uploadPath = staticFileLocation + "/products/" + productId + "/";
            String fileName = file.getOriginalFilename();
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }

            Path destination = Path.of(directory.getAbsolutePath(), fileName);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Image uploaded successfully");
            ProductImage productImage = ProductImage.builder()
                    .image_path(destination.toString())
                    .product(product)
                    .build();

            productImageRepository.save(productImage);

            return ProductImageResponse.builder()
                    .path(destination.toString())
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to upload image");
            return ProductImageResponse.builder()
                    .error("Failed to upload image")
                    .build();
        }
    }

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
                .price(productInformationDto.getProductPrice())
                .build();



        productInformationRepository.save(productInformation);
        return ProductInformationResponse.builder()
                .productId(initProduct.getProductid())
                .message("옷장 정보 추가되었습니다")
                .build();
    }

    public FavResponse addFavProduct(Integer productId){
        //1. 사용자 검색
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        // 옷장 데이터 검색
        Product product = productRepository.findById(productId).orElseThrow();

        // find liked product
        Optional<Fav> likedProd = favRepository.checkCurrentLike(user.getId(), product.getProductid());
        if(likedProd.isPresent()){
            return FavResponse.builder()
                    .error("Error like")
                    .build();
        }

        List<Product> myProducts = productRepository.findAllById(user.getId());

        for(Product myProd : myProducts){
            if(myProd.getProductid().equals(productId)){
                return FavResponse.builder()
                        .error("Error like")
                        .build();
            }
        }

        Fav addFav = Fav.builder()
                .product(product)
                .user(user)
                .build();

        favRepository.save(addFav);
        return FavResponse.builder()
                .message("Liked")
                .build();
    }

    public FavResponse deleteFavProduct(Integer productId){
        //1. 사용자 검색
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        // 옷장 데이터 검색
        Product product = productRepository.findById(productId).orElseThrow();

        // find liked product
        Optional<Fav> likedProd = favRepository.checkCurrentLike(user.getId(), product.getProductid());
        if(likedProd.isPresent()){
            favRepository.delete(likedProd.get());
            return FavResponse.builder()
                    .message("Like removed")
                    .build();
        }else{
            return FavResponse.builder()
                    .error("Error remove like")
                    .build();
        }

    }

    public List<ProductDataResponse> findProduct(String search) {
        //1. 사용자 검색
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        List<ProductDataResponse> productDataResponses = new ArrayList<>();

        List<Product> products = new ArrayList<>();
        List<ProductInformation> productInfo = productInformationRepository.findALlByProductName(search);
        for(ProductInformation pI : productInfo){
            Optional<Product> product = productRepository.findById(pI.getId());
            List<ProductImage> productImages = productImageRepository.findAllImagesByProductId(pI.getId());
            List<ProductImageResponse> imageResponse = new ArrayList<>();
            for(ProductImage pImage : productImages){
                imageResponse.add(ProductImageResponse.builder()
                                .path(pImage.getImage_path())
                        .build());
            }

            productDataResponses.add(ProductDataResponse.builder()
                            .productPrice(pI.getPrice())
                            .productName(pI.getName())
                            .productId(pI.getId())
                            .imagePath(imageResponse)
                            .productInfo(pI.getDescription())
                    .build());
        }
        return productDataResponses;
    }

    public List<MyFavResponse> getFavProd() {
        //1. 사용자 검색
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        List<MyFavResponse> myFavResponses = new ArrayList<>();

        List<Fav> fav = favRepository.getMyFav(user.getId());
        for(Fav f : fav){
            ProductInformation p = productInformationRepository.findInfoById(f.getProduct().getProductid());
            List<ProductImage> pImage = productImageRepository.findAllImagesByProductId(f.getProduct().getProductid());
            myFavResponses.add(MyFavResponse.builder()
                            .productImage(pImage.get(0).getImage_path())
                            .productName(p.getName())
                            .productPrice(p.getPrice())
                            .productId(p.getId())
                    .build());
        }
        return myFavResponses;

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
