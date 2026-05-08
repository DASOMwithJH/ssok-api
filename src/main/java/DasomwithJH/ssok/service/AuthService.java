package DasomwithJH.ssok.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import DasomwithJH.ssok.global.exception.CoreException;
import DasomwithJH.ssok.global.exception.code.UserErrorCode;
import DasomwithJH.ssok.global.security.JwtToken;
import DasomwithJH.ssok.global.security.JwtTokenProvider;
import DasomwithJH.ssok.entity.User;
import DasomwithJH.ssok.dto.authdto.LoginResponse;
import DasomwithJH.ssok.dto.authdto.SignUpRequest;
import DasomwithJH.ssok.dto.authdto.SignUpResponse;
import DasomwithJH.ssok.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userAuthRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponse login(String email, String password) {
        log.info("AuthenticationToken 생성: email={}", email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            email, password);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        log.info("Authentication 성공: email={}", email);
        JwtToken token = jwtTokenProvider.generateToken(authentication);
        log.info("JWT 토큰 생성 완료: email={}", email);

        User user = userAuthRepository.findByEmail(email)
            .orElseThrow(() -> new CoreException(UserErrorCode.USER_NOT_FOUND));

        return new LoginResponse(token.getGrantType(), token.getAccessToken(),
            token.getRefreshToken());
    }

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {

        if (userAuthRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CoreException(UserErrorCode.USER_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        User user = userAuthRepository.save(signUpRequest.toUser(encodedPassword));
        return new SignUpResponse(user.getEmail(), user.getNickname(), user.getRole());
    }
}
