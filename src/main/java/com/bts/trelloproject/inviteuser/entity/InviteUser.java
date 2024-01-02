package com.bts.trelloproject.inviteuser.entity;

import com.bts.trelloproject.board.entity.Boards;
import com.bts.trelloproject.global.common.BaseLastModifiedTimeEntity;
import com.bts.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "inviteuser")
public class InviteUser extends BaseLastModifiedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Boards boards;


    public InviteUser(User findUser, Boards findBoards) {
        this.user = findUser;
        this.boards = findBoards;
    }
}
