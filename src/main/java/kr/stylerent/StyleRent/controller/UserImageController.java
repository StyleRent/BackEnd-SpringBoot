package kr.stylerent.StyleRent.controller;

import kr.stylerent.StyleRent.dto.ImageMessageResponse;
import kr.stylerent.StyleRent.dto.ImageResponse;
import kr.stylerent.StyleRent.entity.ProfileImage;
import kr.stylerent.StyleRent.repository.ProfileImageRepository;
import kr.stylerent.StyleRent.service.UserImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.Blob;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserImageController {
    @Autowired
    UserImageService userImageService;


    // 프로필 사진 추가
    @PostMapping("/api/v1/uploadimage")
    public ResponseEntity<ImageMessageResponse> uploadFile(@RequestParam("image") MultipartFile image) {
        String message = "";
        try {
            userImageService.store(image);

            message = "프로필 사진 추가되었습니다 : " + image.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ImageMessageResponse(message));
        } catch (Exception e) {
            message = "프로필 사진 추가 실패되었습니다 : " + image.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ImageMessageResponse(message));
        }
    }

    @GetMapping("/api/v1/getprofileimage")
    public ResponseEntity<byte[]> getFile() {
        ProfileImage profileImage = userImageService.getProfileImage();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profileImage.getName() + "\"")
                .body(profileImage.getData());
    }

}
