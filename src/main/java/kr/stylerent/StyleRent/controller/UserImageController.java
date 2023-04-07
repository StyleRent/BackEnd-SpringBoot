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

            message = "Uploaded the file successfully: " + image.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ImageMessageResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + image.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ImageMessageResponse(message));
        }
    }

    @GetMapping("/api/v1/userimage")
    public ResponseEntity<List<ImageResponse>> getListFiles() {
        List<ImageResponse> files = userImageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getUser().getId().toString())
                    .toUriString();

            return new ImageResponse(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType());
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }


    @GetMapping("/api/v1/getprofileimage")
    public ResponseEntity<byte[]> getFile() {
        ProfileImage profileImage = userImageService.getProfileImage();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profileImage.getName() + "\"")
                .body(profileImage.getData());
    }

}
