package kr.stylerent.StyleRent.controller;

import kr.stylerent.StyleRent.dto.*;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.entity.UserData;
import kr.stylerent.StyleRent.repository.UserRepository;
import kr.stylerent.StyleRent.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserRepository userRepository;

    private final UserDataService userDataService;

    @GetMapping("/user")
    public Map<String, Object> hi(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        User user1 = userRepository.findById(1).orElseThrow();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstname", user.getUsername());
        userMap.put("email", user.getEmail());
        userMap.put("password", user.getPassword());
        userMap.put("User find by id ->", user1.getId());
        userMap.put("User find by id name ->", user1.getUsername());
        return userMap;
    }

    @PostMapping("/api/v1/setuserdata")
    public ResponseEntity<UserDataResponse> setUserData(
            @RequestBody UserDataDto request
    ) {

        return ResponseEntity.ok(userDataService.setPhoneNumber(request));
    }

    @GetMapping("/api/v1/getuserdata")
    public ResponseEntity<UserDataResponse> getUserData(){
        return ResponseEntity.ok(userDataService.getUserData());
    }

}
