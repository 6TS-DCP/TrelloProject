package com.bts.trelloproject.board.controller;

import com.bts.trelloproject.board.dto.BoardRequestDto;
import com.bts.trelloproject.board.dto.BoardResponseDto;
import com.bts.trelloproject.board.service.BoardService;
import com.bts.trelloproject.global.common.CustomResponseEntity;
import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.security.userdetails.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;



    //보드 생성
    @PostMapping
    public ResponseEntity<CustomResponseEntity> boardBoard(@RequestBody BoardRequestDto boardrequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        BoardResponseDto responseDto = boardService.createBoard(boardrequestDto, userDetails.getUsername());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_CREATE_BOARD);
    }

    //선택 보드 조회
    @GetMapping("/{boardId}")
    public BoardResponseDto getboard(@PathVariable Long boardId) {
        BoardResponseDto boardResponseDto = boardService.getBoard(boardId);
        return boardResponseDto;

    }

    //보드 전체 조회
    @GetMapping
    public List<BoardResponseDto> getAllboard(){
        List<BoardResponseDto> boardResponseDtoList= boardService.getAllBoard();
        return boardResponseDtoList;
    }

    //게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<CustomResponseEntity> updateboard(@PathVariable Long boardId,
                                                        @RequestBody BoardRequestDto boardRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
            BoardResponseDto responseDto = boardService.updateBoard(boardId,boardRequestDto,userDetails.getUsername());
            return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_UPDATE_BOARD);
    }

    //게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<CustomResponseEntity> deleteboard(@PathVariable Long boardId,  @AuthenticationPrincipal UserDetailsImpl userDetails){
            boardService.deleteBoard(boardId, userDetails.getUsername());
            return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_DELETE_BOARD);
    }
}
