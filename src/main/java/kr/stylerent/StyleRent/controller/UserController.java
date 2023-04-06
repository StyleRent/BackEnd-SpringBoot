package kr.stylerent.StyleRent.controller;

import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

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
}
