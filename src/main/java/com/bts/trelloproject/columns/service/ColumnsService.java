package com.bts.trelloproject.columns.service;

import com.bts.trelloproject.board.entity.Boards;
import com.bts.trelloproject.board.service.BoardService;
import com.bts.trelloproject.columns.dto.ColumnsRequestDto;
import com.bts.trelloproject.columns.dto.ColumnsResponseDto;
import com.bts.trelloproject.columns.dto.ColumnsSeqRequestDto;
import com.bts.trelloproject.columns.entity.Columns;
import com.bts.trelloproject.columns.repository.ColumnsRepository;
import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.exception.CustomException;
import com.bts.trelloproject.user.entity.User;
import com.bts.trelloproject.user.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColumnsService {
    private final ColumnsRepository columnsRepository;
    private final UserService userService;
    private final BoardService boardService;

    public void createColumns(Long boardId, ColumnsRequestDto columnsRequestDto, User user) {

        checkSeq(columnsRequestDto.getColumn_seq(), user);
        Boards boards = boardService.findById(boardId);
        Columns columns = new Columns(boards, columnsRequestDto, user);
        columnsRepository.save(columns);
    }

    public List<ColumnsResponseDto> getMyColumns(Long boardId, User user) {

        User dbuser = userService.findById(user.getUserId());

        Boards boards = boardService.findById(boardId);
        return columnsRepository.findAllByUserAndBoards(dbuser,boards).stream().map(ColumnsResponseDto::new).toList();
    }

    public ColumnsResponseDto getMyColumn(Long boardId, Long columnId, User user) {

        userService.findById(user.getUserId());
        Boards boards = boardService.findById(boardId);

        checkMyColumn(columnId, user);

        Columns columns = columnsRepository.findByIdAndBoards(columnId, boards);

        return new ColumnsResponseDto(columns);
    }

    @Transactional
    public void updateColumn(Long boardId, Long columnId, ColumnsRequestDto columnsRequestDto, User user) {

        Boards boards = boardService.findById(boardId);
        Columns columns = columnsRepository.findByIdAndBoards(columnId, boards);

        if (!columns.getUser().getUsername().equals(user.getUsername())) {
            throw new CustomException(StatusEnum.COLUMN_NOT_MATCHED);
        }

        columns.update(columnsRequestDto);
    }

    public void deleteColumn(Long boardId, Long columnId, User user) {

        Boards boards = boardService.findById(boardId);
        Columns columns = columnsRepository.findByIdAndBoards(columnId, boards);

        if (!columns.getUser().getUsername().equals(user.getUsername())) {
            throw new CustomException(StatusEnum.COLUMN_NOT_MATCHED);
        }

        columnsRepository.delete(columns);
    }

    public List<ColumnsResponseDto> getColumns() {

        return columnsRepository.findAll().stream().map(ColumnsResponseDto::new).toList();
    }

    public ColumnsResponseDto getColumn(Long id) {

        Columns columns = columnsRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.POST_NOT_FOUND));
        return new ColumnsResponseDto(columns);
    }

    @Transactional
    public void sequenceChangeColumn(Long id, ColumnsSeqRequestDto columnsRequestDto, User user) {
        Columns columns = columnsRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.COLUMN_NOT_FOUND));

        if(changeColumn(columnsRequestDto.getColumnSeq(), user)) {
            Columns changeSeqColumn = columnsRepository.findByColumnSeq(columnsRequestDto.getColumnSeq());
            changeSeqColumn.updateSeq(columns.getColumnSeq());
        }

        columns.updateSeq(columnsRequestDto.getColumnSeq());
    }

    public boolean changeColumn(int columnSeq, User user) {
        Optional<Columns> checkSeq = columnsRepository.findByColumnSeqAndUser(columnSeq, user);
        if(checkSeq.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    private void checkMyColumn(Long id, User user) {
        Optional<Columns> checkColumn = columnsRepository.findByIdAndUser(id, user);
        if(checkColumn.isEmpty()) {
            throw new CustomException(StatusEnum.COLUMN_NOT_FOUND);
        }
    }

    public void checkSeq(int columnSeq, User user) {
        Optional<Columns> checkSeq = columnsRepository.findByColumnSeqAndUser(columnSeq, user);
        if(checkSeq.isPresent()) {
            throw new CustomException(StatusEnum.DUPLICATED_COLUMN_SEQ);
        }
    }

    public Columns findById(Long columnId) {
        return columnsRepository.findById(columnId).orElseThrow(() -> new CustomException(StatusEnum.COLUMN_NOT_FOUND));
    }


}
