package com.bts.trelloproject.user.repository;

import com.bts.trelloproject.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    Boolean existsByUsername(String username);

    User findByOauthId(String oauthId);

    User save(User user);
}
