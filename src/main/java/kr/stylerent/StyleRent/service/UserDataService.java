package kr.stylerent.StyleRent.service;

import kr.stylerent.StyleRent.dto.ImageResponse;
import kr.stylerent.StyleRent.dto.UserDataDto;
import kr.stylerent.StyleRent.dto.UserDataResponse;
import kr.stylerent.StyleRent.entity.ProfileImage;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.entity.UserData;
import kr.stylerent.StyleRent.repository.ProfileImageRepository;
import kr.stylerent.StyleRent.repository.UserDataRepository;
import kr.stylerent.StyleRent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final UserDataRepository userDataRepository;

    private final ProfileImageRepository profileImageRepository;

    private final UserRepository userRepository;
    public UserDataResponse setPhoneNumber(UserDataDto request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        UserData userData = UserData.builder()
                .user(user)
                .phonenumber(request.getPhonenumber())
                .build();

        UserData saved = userDataRepository.save(userData);

        return UserDataResponse.builder()
                .userid(saved.getUserdataid())
                .phonenumber(request.getPhonenumber())
                .build();
    }

    public UserDataResponse getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        UserData userData = userDataRepository.findById(user.getId()).orElseThrow();
        ProfileImage profileImage = profileImageRepository.findById(user.getId()).orElseThrow();
        System.out.println(" Profile image --->> " + profileImage.getData());
        ImageResponse imageResponse = ImageResponse.builder()
                .name(profileImage.getName())
                .uri("/file/" + profileImage.getUser().getId().toString())
                .type(profileImage.getType())
                .build();


        return UserDataResponse.builder()
                .userid(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phonenumber(userData.getPhonenumber())
                .imageResponse(imageResponse)
                .build();
    }
}
