package com.bts.trelloproject.board.service;

import com.bts.trelloproject.board.dto.BoardRequestDto;
import com.bts.trelloproject.board.dto.BoardResponseDto;
import com.bts.trelloproject.board.entity.Boards;
import com.bts.trelloproject.board.repository.BoardRepository;
import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.exception.CustomException;
import com.bts.trelloproject.user.entity.User;
import com.bts.trelloproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardResponseDto createBoard(BoardRequestDto dto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Boards boards = new Boards(dto, user);

        boardRepository.save(boards);
        return new BoardResponseDto(boards);
    }


    public BoardResponseDto getBoard(Long boardId) {
        Boards boards = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(StatusEnum.BOARD_NOT_FOUND));
        return new BoardResponseDto(boards);
    }


    public List<BoardResponseDto> getAllBoard() {
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        //Entity리스트 조회
        List<Boards> boardsList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdTime"));
        //Entity List -> Dto List
        boardsList.forEach(boards -> {
            var boardDto = new BoardResponseDto(boards);
            boardResponseDtoList.add(boardDto);
        });
        return boardResponseDtoList;
    }

    //게시글 수정
    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto dto, String username) {
        Boards boards = checkLoginUserAndBoardUser(boardId, username);
        boards.updateboard(dto);

        boardRepository.save(boards);
        return new BoardResponseDto(boards);
    }


    //게시글 삭제
    public void deleteBoard(Long boardId, String username) {
        Boards boards = checkLoginUserAndBoardUser(boardId, username);
        //delete
        boardRepository.deleteById(boardId);
        return;
    }

    private Boards checkLoginUserAndBoardUser(Long boardId, String username) {

        Boards boards = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(StatusEnum.BOARD_NOT_FOUND));
        boardRepository.findByUser_Username(username).orElseThrow(() -> new IllegalArgumentException("로그인한 사용자가 작성한 게시물이 없습니다."));
        if (!username.equals(boards.getUser().getUsername())) {
            throw new IllegalArgumentException("작성자만 보드를 수정/삭제 할 수 있습니다.");
        }
        return boards;
    }

}