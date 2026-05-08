package DasomwithJH.ssok.global.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import DasomwithJH.ssok.entity.User;
import DasomwithJH.ssok.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("사용자 정보 조회: username={}", username);
        return userRepository.findByEmail(username)
            .map(user -> {
                log.info("사용자 발견: username={}", username);
                return createUserDetails(user);
            })
            .orElseThrow(() -> {
                log.warn("사용자 없음: username={}", username);
                return new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다.");
            });
    }

    private UserDetails createUserDetails(User user) {
        return new CustomUserDetails(user);
    }
}

