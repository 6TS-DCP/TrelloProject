package com.bts.trelloproject.comment.service;

import com.bts.trelloproject.board.entity.Boards;
import com.bts.trelloproject.board.service.BoardService;
import com.bts.trelloproject.card.Service.CardService;
import com.bts.trelloproject.card.entity.Card;
import com.bts.trelloproject.columns.entity.Columns;
import com.bts.trelloproject.columns.service.ColumnsService;
import com.bts.trelloproject.comment.entity.Comment;
import com.bts.trelloproject.comment.repository.CommentRepository;
import com.bts.trelloproject.user.entity.User;
import com.bts.trelloproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CardService cardService;
    private final UserService userService;
    private final ColumnsService columnsService;
    private final BoardService boardService;
    private final CommentRepository commentRepository;

    public void createComment(Long userId, Long boardId, Long columnsId, Long cardId, String commentContent) {
        User findUser = userService.findById(userId);
        Columns findColumns = columnsService.findById(columnsId);
        Card findCard = cardService.findById(cardId);
        Boards findBoard = boardService.findById(boardId);

        Comment comment = new Comment(findUser, findBoard, findColumns, findCard, commentContent);

        commentRepository.save(comment);
    }
}