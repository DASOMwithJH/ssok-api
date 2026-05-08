package DasomwithJH.ssok.dto.authdto;


import lombok.AllArgsConstructor;
import lombok.Data;
import DasomwithJH.ssok.entity.enums.UserRole;

@Data
@AllArgsConstructor
public class SignUpResponse {
    String email;
    String nickname;
    UserRole role;
}
