package com.bts.trelloproject.columns.controller;

import com.bts.trelloproject.columns.dto.ColumnsRequestDto;
import com.bts.trelloproject.columns.dto.ColumnsResponseDto;
import com.bts.trelloproject.columns.service.ColumnsService;
import com.bts.trelloproject.global.common.CustomResponseEntity;
import com.bts.trelloproject.global.common.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ColumnsConroller {

    private final ColumnsService columnsService;

    @PostMapping("/columns") // 컬럼 생성
    public ResponseEntity<CustomResponseEntity> createColumn(@RequestBody ColumnsRequestDto columnsRequestDto) {
        columnsService.createColumns(columnsRequestDto);

        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_CREATE_COLUMN);
    }

    @GetMapping("/columns") // 컬럼 전체조회
    public List<ColumnsResponseDto> getColums() {
        return columnsService.getColums();
    }

    @GetMapping("/columns/{id}") // 컬럼 단건조회
    public ColumnsResponseDto getColumn(@PathVariable Long id) {
        return columnsService.getColum(id);
    }

    @PatchMapping("/columns/{id}") // 컬럼 수정
    public ResponseEntity<CustomResponseEntity> updateColumn(@PathVariable Long id, @RequestBody ColumnsRequestDto columnsRequestDto) {
        columnsService.updateColumn(id, columnsRequestDto);
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_UPDATE_COLUMN);
    }

    @DeleteMapping("/columns/{id}") // 컬럼 삭제
    public ResponseEntity<CustomResponseEntity> deleteColumn(@PathVariable Long id) {
        columnsService.removeColumn(id);
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_DELETE_COLUMN);
    }



}
