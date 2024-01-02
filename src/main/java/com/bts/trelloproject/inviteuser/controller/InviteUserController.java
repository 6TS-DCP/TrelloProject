package com.bts.trelloproject.inviteuser.controller;

import com.bts.trelloproject.global.common.CustomResponseEntity;
import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.security.userdetails.UserDetailsImpl;
import com.bts.trelloproject.inviteuser.service.InviteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards/{boardsId}/{inviteId}/inviteuser")
@RequiredArgsConstructor
public class InviteUserController {

    private final InviteUserService inviteUserService;

    @PostMapping
    public ResponseEntity<CustomResponseEntity> InviteUsers(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                              @PathVariable Long boardsId, @PathVariable Long inviteId) {

        Long userId = userDetails.getUser().getUserId();
        inviteUserService.InviteUsers(userId, boardsId, inviteId);
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_COMMENT_CREATE);
    }
}
