package com.bts.trelloproject.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private String commentContent;

    public CommentRequestDto(String commentContent) {
        this.commentContent = commentContent;
    }
}