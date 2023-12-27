package com.bts.trelloproject.user.service;

import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.exception.CustomException;
import com.bts.trelloproject.global.security.jwt.JwtUtil;
import com.bts.trelloproject.user.dto.LoginDTO;
import com.bts.trelloproject.user.dto.SignupDTO;
import com.bts.trelloproject.user.entity.User;
import com.bts.trelloproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public User findById(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new CustomException(StatusEnum.USER_NOT_FOUND));
    }

    public void signup(SignupDTO signRequestDTO) {

        validateExistingUser(signRequestDTO.username());

        User user = signRequestDTO.toEntity(passwordEncoder);

        userRepository.save(user);
        //savePasswordHistory(user.getUserId(), user.getPassword());
    }

    private void validateExistingUser(String userName) {
        userRepository.findByUsername(userName).ifPresent(user -> {
            throw new CustomException(StatusEnum.ALREADY_EXIST_USER_NAME_EXCEPTION);
        });
    }

    public void login(LoginDTO.Request loginRequestDTO, HttpServletResponse response) {

        String username = loginRequestDTO.username();
        String password = loginRequestDTO.password();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(StatusEnum.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(StatusEnum.USER_NOT_FOUND);
        }

        response.setHeader("Authorization", jwtUtil.createToken(username, true));
    }

    public void logout(HttpServletResponse response) {

        response.setHeader("Authorization", jwtUtil.createToken(null, false));
    }

//    private void savePasswordHistory(Long userId, String password) {
//
//        PasswordHistory passwordHistory =
//                PasswordHistory.createPasswordHistory(password, userId);
//
//        passwordHistoryService.savePasswordHistory(passwordHistory);
//    }

}
