package kr.stylerent.StyleRent.service;

import kr.stylerent.StyleRent.entity.ProfileImage;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.repository.ProfileImageRepository;
import kr.stylerent.StyleRent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserImageService {
    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Autowired
    private UserRepository userRepository;


    // 프로필 사진을 데이터베이스에서 저장
    public ProfileImage store(MultipartFile image) throws IOException {
        // Get Image name
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        // Get authenticated token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // find current User by Email
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        // Check on Update or Add current user profile image
        if(profileImageRepository.findById(user.getId()).isPresent()){
            // If user profile image has been existing
            // 1.1 Find current Image
            ProfileImage updatingImage = ProfileImage.builder().user(user).name(fileName).type(image.getContentType()).data(image.getBytes()).build();
            // Update Image Object
            ProfileImage profileImage = profileImageRepository.findById(user.getId()).orElse(new ProfileImage());
            profileImage.updateImage(updatingImage);
            // Save updated profile image on Database
            return profileImageRepository.save(profileImage);

        }else{
            // Create new Profile Image by constructor
            ProfileImage profileImage = ProfileImage.builder().user(user).name(fileName).type(image.getContentType()).data(image.getBytes()).build();
            // Save created profile Image object on Database
            return profileImageRepository.save(profileImage);
        }
    }

    public Stream<ProfileImage> getAllFiles() {
        return profileImageRepository.findAll().stream();
    }

    public ProfileImage getProfileImage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        return profileImageRepository.findById(user.getId()).get(); // get image table
    }

}
