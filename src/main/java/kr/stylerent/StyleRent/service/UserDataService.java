package kr.stylerent.StyleRent.service;

import kr.stylerent.StyleRent.dto.UserDataDto;
import kr.stylerent.StyleRent.dto.UserDataResponse;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.entity.UserData;
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
        // Find coordinate
        // find Profile image

        return UserDataResponse.builder()
                .userid(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phonenumber(userData.getPhonenumber())
                .build();
    }
}
