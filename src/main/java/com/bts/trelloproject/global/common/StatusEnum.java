package com.bts.trelloproject.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum StatusEnum {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "사용자가 존재하지 않습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "게시글이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND", "댓글이 존재하지 않습니다."),
    COMMENT_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "COMMENT_NOT_MATCHED", "댓글을 작성한 사용자가 아닙니다."),
    POST_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "POST_NOT_MATCHED", "게시글을 작성한 사용자가 아닙니다."),
    SUCCESS_CREATE_POST(HttpStatus.CREATED, "SUCCESS_CREATE_POST", "게시물을 작성하였습니다."),
    SUCCESS_UPDATE_POST(HttpStatus.CREATED, "SUCCESS_UPDATE_POST", "게시물을 수정하였습니다."),
    SUCCESS_DELETE_POST(HttpStatus.OK, "SUCCESS_DELETE_POST", "공고가 삭제되었습니다."),
    SUCCESS_JOIN(HttpStatus.CREATED, "SUCCESS_JOIN", "회원가입에 성공하였습니다."),
    SUCCESS_LOGIN(HttpStatus.OK, "SUCCESS_LOGIN", "로그인에 성공하였습니다."),
    SUCCESS_LOGOUT(HttpStatus.OK, "SUCCESS_LOGOUT", "로그아웃하였습니다."),
    SUCCESS_LIKE(HttpStatus.CREATED, "SUCCESS_LIKE", "좋아요가 추가되었습니다."),
    SUCCESS_LIKE_DELETE(HttpStatus.OK, "SUCCESS_LIKE_DELETE", "좋아요가 삭제되었습니다."),
    SUCCESS_COMMENT_CREATE(HttpStatus.OK, "SUCCESS_COMMENT_CREATE", "댓글이 등록되었습니다."),
    SUCCESS_COMMENT_UPDATE(HttpStatus.OK, "SUCCESS_COMMENT_UPDATE", "댓글이 수정되었습니다."),
    SUCCESS_COMMENT_DELETE(HttpStatus.OK, "SUCCESS_DELETE_POST", "댓글이 삭제되었습니다."),
    TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "TOKEN_NOT_VALID", "토큰이 유효하지 않습니다."),
    FORBIDDEN_AUTH(HttpStatus.FORBIDDEN, "FORBIDDEN_AUTH", "접근 권한이 없습니다."),
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "DUPLICATED_LOGIN_ID", "중복된 아이디가 존재합니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "DUPLICATED_EMAIL", "중복된 이메일이 존재합니다."),
    BadCredentialsException(HttpStatus.UNAUTHORIZED, "PASSWORD_NOT_MATCHED", "비밀번호가 일치하지 않습니다."),
    NotEqualsCheckPassWordException(HttpStatus.BAD_REQUEST, "PASSWORD_CHECK_FAIL", "입력하신 비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    EqualsCURRENTPassWordException(HttpStatus.BAD_REQUEST, "PASSWORD_EQUALS_CURRENT", "현재 비밀번호와 동일합니다."),
    UsernameNotFoundException(HttpStatus.BAD_REQUEST, "USER_NOT_FOUND", "등록된 아이디가 존재하지 않습니다."),
    FAIL_CREATE_MESSAGE(HttpStatus.BAD_REQUEST, "FAIL_CREATE_MESSAGE", "인증 코드 메일 생성 실패 했습니다."),
    FAIL_SEND_EMAIL(HttpStatus.BAD_REQUEST, "FAIL_SEND_EMAIL", "인증 코드 메일이 전송 실패 했습니다."),
    FAIL_EMAIL_CONFIRM(HttpStatus.UNAUTHORIZED, "FAIL_EMAIL_CONFIRM", "이메일 인증을 하지 않았습니다."),
    SENDER_UNMATCHED(HttpStatus.BAD_REQUEST, "SENDER_UNMATCHED", "보내는 사람이 잘못 설정 되었습니다."),

    // COLUMN
    SUCCESS_CREATE_COLUMN(HttpStatus.CREATED, "SUCCESS_CREATE_COLUMN", "컬럼을 작성하였습니다."),
    COLUMN_NOT_FOUND(HttpStatus.NOT_FOUND, "COLUMN_NOT_FOUND", "컬림이 존재하지 않습니다."),
    SUCCESS_UPDATE_COLUMN(HttpStatus.CREATED, "SUCCESS_UPDATE_COLUMN", "컬럼을 수정하였습니다."),
    SUCCESS_DELETE_COLUMN(HttpStatus.OK, "SUCCESS_DELETE_COLUMN", "컬럼이 삭제되었습니다."),
    COLUMN_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "COLUMN_NOT_MATCHED", "컬럼을 작성한 사용자가 아닙니다."),
    ;


    private final HttpStatus httpStatus;
    private final String description;
    private final String message;
}