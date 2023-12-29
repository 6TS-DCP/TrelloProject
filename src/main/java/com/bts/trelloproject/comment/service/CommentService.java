package com.bts.trelloproject.comment.service;

import com.bts.trelloproject.card.Service.CardService;
import com.bts.trelloproject.card.entity.Card;
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
    private final CommentRepository commentRepository;

    public void createComment(Long userId, Long cardId, String commentContent) {
        User findUser = userService.findById(userId);
        Card findCard = cardService.findById(cardId);

        Comment comment = new Comment(findUser, findCard, commentContent);

        commentRepository.save(comment);
    }
}