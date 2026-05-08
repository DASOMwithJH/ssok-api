package DasomwithJH.ssok.dto.authdto;

import lombok.Data;
import DasomwithJH.ssok.entity.User;
import DasomwithJH.ssok.entity.enums.UserRole;

@Data
public class SignUpRequest {

    String email;
    String password;
    String nickname;

    public User toUser(String encodedPassword) {
        return User.builder()
            .email(email)
            .passwordHash(encodedPassword)
            .nickname(nickname)
            .role(UserRole.USER)
            .build();
    }
}


