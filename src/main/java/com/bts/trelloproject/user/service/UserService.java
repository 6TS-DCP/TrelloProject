package com.bts.trelloproject.user.service;

import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.exception.CustomException;
import com.bts.trelloproject.global.security.jwt.JwtUtil;
import com.bts.trelloproject.global.security.userdetails.UserDetailsImpl;
import com.bts.trelloproject.passwordhistory.entity.PasswordHistory;
import com.bts.trelloproject.passwordhistory.service.PasswordHistoryService;
import com.bts.trelloproject.user.dto.ChangePasswordDTO;
import com.bts.trelloproject.user.dto.LoginDTO;
import com.bts.trelloproject.user.dto.SignupDTO;
import com.bts.trelloproject.user.dto.UpdateProfileDTO;
import com.bts.trelloproject.user.entity.User;
import com.bts.trelloproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final PasswordHistoryService passwordHistoryService;

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

    @Transactional
    public void updateProfile(UpdateProfileDTO updateProfileDTO, UserDetailsImpl userDetails) {

        User user = userRepository.findById(userDetails.getUser().getUserId())
                .orElseThrow(() -> new CustomException(StatusEnum.USER_NOT_FOUND));

        if (!passwordEncoder.matches(updateProfileDTO.password(), user.getPassword())) {
            throw new CustomException(StatusEnum.WRONG_PASSWORD_EXCEPTION);
        }

        String introduce = updateProfileDTO.introduce();

        user.updateProfile(introduce);
    }

    @Transactional
    public void changePassword(ChangePasswordDTO changePasswordDTO, Long userId) {

        String existingPassword = changePasswordDTO.existingPassword();
        String newPassword = changePasswordDTO.newPassword();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(StatusEnum.USER_NOT_FOUND));

        validateChangingPassword(user, existingPassword, newPassword);

        user.changePassword(passwordEncoder.encode(newPassword));
        savePasswordHistory(userId, newPassword);
    }

    private void savePasswordHistory(Long userId, String password) {

        PasswordHistory passwordHistory =
                PasswordHistory.createPasswordHistory(password, userId);

        passwordHistoryService.savePasswordHistory(passwordHistory);
    }

    private boolean isPasswordWithinLast3Times(Long userId, String newPassword) {

        List<PasswordHistory> passwordHistoryList =
                passwordHistoryService.findTop3PasswordHistory(userId);

        for (PasswordHistory history : passwordHistoryList) {
            if (passwordEncoder.matches(newPassword, history.getPassword())) {
                return false;
            }
        }
        return true;
    }

    private void validateChangingPassword(User user, String existingPassword, String newPassword) {

        if (!passwordEncoder.matches(existingPassword, user.getPassword())) {
            throw new CustomException(StatusEnum.WRONG_PASSWORD_EXCEPTION);
        }

        if (!isPasswordWithinLast3Times(user.getUserId(), newPassword)) {
            throw new CustomException(StatusEnum.DUPLICATE_PASSWORD_EXCEPTION);
        }
    }

}
