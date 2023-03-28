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
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstname", user.getUsername());
        userMap.put("email", user.getEmail());
        userMap.put("password", user.getPassword());
        return userMap;
    }
}
