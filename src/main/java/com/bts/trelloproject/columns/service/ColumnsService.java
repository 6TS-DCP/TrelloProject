package com.bts.trelloproject.columns.service;

import com.bts.trelloproject.columns.dto.ColumnsRequestDto;
import com.bts.trelloproject.columns.dto.ColumnsResponseDto;
import com.bts.trelloproject.columns.entity.Columns;
import com.bts.trelloproject.columns.repository.ColumnsRepository;
import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColumnsService {
    private final ColumnsRepository columnsRepository;

    public void createColumns(ColumnsRequestDto columnsRequestDto) {
        Columns columns = new Columns(columnsRequestDto);
        columnsRepository.save(columns);
    }

    public List<ColumnsResponseDto> getColums() {
        return columnsRepository.findAll().stream().map(ColumnsResponseDto::new).toList();
    }

    public ColumnsResponseDto getColum(Long id) {
        Columns columns = columnsRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.COLUMN_NOT_FOUND));
        return new ColumnsResponseDto(columns);
    }

    @Transactional
    public void updateColumn(Long id, ColumnsRequestDto columnsRequestDto) {
        Columns columns = columnsRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.COLUMN_NOT_FOUND));

//        if (!columns.getUser().getUsername().equals(user.getUsername())) {
//            throw new CustomException(StatusEnum.COLUMN_NOT_MATCHED);
//        }


        columns.update(columnsRequestDto);
    }

    public void removeColumn(Long id) {
        Columns columns = columnsRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.COLUMN_NOT_FOUND));

//        if (!columns.getUser().getUsername().equals(user.getUsername())) {
//            throw new CustomException(StatusEnum.COLUMN_NOT_MATCHED);
//        }


        columnsRepository.delete(columns);
    }
}
