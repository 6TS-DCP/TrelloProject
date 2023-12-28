package com.bts.trelloproject.user.controller;

import com.bts.trelloproject.global.common.CustomResponseEntity;
import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.security.userdetails.UserDetailsImpl;
import com.bts.trelloproject.user.dto.ChangePasswordDTO;
import com.bts.trelloproject.user.dto.LoginDTO;
import com.bts.trelloproject.user.dto.SignupDTO;
import com.bts.trelloproject.user.dto.UpdateProfileDTO;
import com.bts.trelloproject.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CustomResponseEntity> signup(@Valid @RequestBody SignupDTO signRequestDTO) {

        userService.signup(signRequestDTO);

        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_JOIN);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomResponseEntity> login(@RequestBody LoginDTO.Request loginRequestDTO, HttpServletResponse response) {

        userService.login(loginRequestDTO, response);

        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_LOGIN);
    }

    @GetMapping("/logout")
    public ResponseEntity<CustomResponseEntity> logout(HttpServletResponse response) {

        userService.logout(response);

        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_LOGOUT);
    }

    @PatchMapping("/profile")
    public ResponseEntity<CustomResponseEntity> updateProfile(@Valid @RequestBody UpdateProfileDTO updateProfileDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        userService.updateProfile(updateProfileDTO, userDetails);

        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_USER_UPDATE);
    }

    @PatchMapping("/password")
    public ResponseEntity<CustomResponseEntity> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        userService.changePassword(changePasswordDTO, userDetails.getUser().getUserId());

        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_USER_UPDATE);
    }
}

