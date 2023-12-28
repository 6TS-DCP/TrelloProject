package com.bts.trelloproject.columns.service;

import com.bts.trelloproject.columns.dto.ColumnsRequestDto;
import com.bts.trelloproject.columns.dto.ColumnsResponseDto;
import com.bts.trelloproject.columns.dto.ColumnsSeqRequestDto;
import com.bts.trelloproject.columns.entity.Columns;
import com.bts.trelloproject.columns.repository.ColumnsRepository;
import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.exception.CustomException;
import com.bts.trelloproject.user.entity.User;
import com.bts.trelloproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ColumnsService {
    private final ColumnsRepository columnsRepository;
    private final UserRepository userRepository;

    public void createColumns(ColumnsRequestDto columnsRequestDto, User user) {
        checkSeq(columnsRequestDto.getColumn_seq(), user);
        Columns columns = new Columns(columnsRequestDto, user);
        columnsRepository.save(columns);
    }



    public List<ColumnsResponseDto> myGetColumns(User user) {
        User dbuser = userRepository.findById(user.getUserId()).orElseThrow(()
                -> new CustomException(StatusEnum.UsernameNotFoundException));

        return columnsRepository.findAllByUser(dbuser).stream().map(ColumnsResponseDto::new).toList();
    }

    public ColumnsResponseDto myGetColumn(Long id, User user) {
        userRepository.findById(user.getUserId()).orElseThrow(() ->
                new CustomException(StatusEnum.UsernameNotFoundException));
        checkMyColumn(id, user);

        Columns columns = columnsRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.COLUMN_NOT_FOUND));

        return new ColumnsResponseDto(columns);
    }



    @Transactional
    public void updateColumn(Long id, ColumnsRequestDto columnsRequestDto, User user) {
        Columns columns = columnsRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.COLUMN_NOT_FOUND));

        if (!columns.getUser().getUsername().equals(user.getUsername())) {
            throw new CustomException(StatusEnum.COLUMN_NOT_MATCHED);
        }

        columns.update(columnsRequestDto);
    }

    public void removeColumn(Long id, User user) {
        Columns columns = columnsRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.COLUMN_NOT_FOUND));

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
        if(checkColumn.isPresent()) {
            throw new CustomException(StatusEnum.COLUMN_NOT_FOUND);
        }
    }
    public void checkSeq(int columnSeq, User user) {
        Optional<Columns> checkSeq = columnsRepository.findByColumnSeqAndUser(columnSeq, user);
        if(checkSeq.isPresent()) {
            throw new CustomException(StatusEnum.DUPLICATED_COLUMN_SEQ);
        }
    }


}
