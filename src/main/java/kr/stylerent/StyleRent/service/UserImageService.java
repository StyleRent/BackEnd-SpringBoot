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
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        ProfileImage profileImage = ProfileImage.builder().user(user).name(fileName).type(image.getContentType()).data(image.getBytes()).build();

        return profileImageRepository.save(profileImage);
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
