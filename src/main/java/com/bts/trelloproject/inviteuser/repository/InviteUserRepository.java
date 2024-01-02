package com.bts.trelloproject.inviteuser.repository;

import com.bts.trelloproject.inviteuser.entity.InviteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteUserRepository extends JpaRepository<InviteUser, Long> {

    InviteUser findByUser_UserId(Long userId);
}
