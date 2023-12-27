package com.bts.trelloproject.global.security.userdetails;

import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.exception.CustomException;
import com.bts.trelloproject.user.entity.User;
import com.bts.trelloproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsImpl getUserDetails(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(StatusEnum.USER_NOT_FOUND));

        return new UserDetailsImpl(user);
    }
}

//        throw new CustomException(StatusEnum.USER_NOT_FOUND)
// .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));

