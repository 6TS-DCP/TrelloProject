package com.bts.trelloproject.inviteuser.service;

import com.bts.trelloproject.board.entity.Boards;
import com.bts.trelloproject.board.service.BoardService;
import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.exception.CustomException;
import com.bts.trelloproject.inviteuser.entity.InviteUser;
import com.bts.trelloproject.inviteuser.repository.InviteUserRepository;
import com.bts.trelloproject.user.entity.User;
import com.bts.trelloproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InviteUserService {

    private final BoardService boardService;
    private final UserService userService;
    private final InviteUserRepository inviteuserRepository;

    public void InviteUsers(Long userId, Long boardId, Long inviteId) {
        User findUser = userService.findById(inviteId);
        Boards findBoards = boardService.findById(boardId);
        Optional<InviteUser> checkInviteuser = inviteuserRepository.findByUser_UserId(inviteId);
        if(checkInviteuser.isPresent()) { // 값이 있으면
            throw new CustomException(StatusEnum.BOARD_NOT_FOUND);
        }
        InviteUser inviteuser = new InviteUser(findUser, findBoards);

        findBoards.updateInviteUser(inviteuser);
        inviteuserRepository.save(inviteuser);
    }
}
