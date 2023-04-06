package kr.stylerent.StyleRent.dto;

import jakarta.persistence.Column;
import kr.stylerent.StyleRent.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UserDto {
    private String username;
    private String email;
    private String password;

}
