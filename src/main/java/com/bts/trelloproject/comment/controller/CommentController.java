package com.bts.trelloproject.comment.controller;

import com.bts.trelloproject.comment.dto.CommentRequestDto;
import com.bts.trelloproject.comment.service.CommentService;
import com.bts.trelloproject.global.common.CustomResponseEntity;
import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards/{cardId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CustomResponseEntity> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long cardId,
            @RequestBody CommentRequestDto commentRequestDto) {

        Long userId = userDetails.getUser().getUserId();
        commentService.createComment(userId, cardId, commentRequestDto.getCommentContent());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_COMMENT_CREATE);
    }
}
