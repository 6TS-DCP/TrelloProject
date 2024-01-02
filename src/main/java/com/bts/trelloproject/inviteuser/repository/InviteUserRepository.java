package com.bts.trelloproject.inviteuser.repository;

import com.bts.trelloproject.inviteuser.entity.InviteUser;
import com.bts.trelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InviteUserRepository extends JpaRepository<InviteUser, Long> {

    Optional<InviteUser> findByUser_UserId(Long userId);
}
