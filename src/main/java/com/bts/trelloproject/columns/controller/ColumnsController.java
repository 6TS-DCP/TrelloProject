package com.bts.trelloproject.columns.controller;

import com.bts.trelloproject.columns.dto.ColumnsRequestDto;
import com.bts.trelloproject.columns.dto.ColumnsResponseDto;
import com.bts.trelloproject.columns.dto.ColumnsSeqRequestDto;
import com.bts.trelloproject.columns.service.ColumnsService;
import com.bts.trelloproject.global.common.CustomResponseEntity;
import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ColumnsController {

    private final ColumnsService columnsService;

    @PostMapping("/columns") // 컬럼 생성
    public ResponseEntity<CustomResponseEntity> createColumn(@RequestBody ColumnsRequestDto columnsRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        columnsService.createColumns(columnsRequestDto, userDetails.getUser());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_CREATE_COLUMN);
    }

    @GetMapping("/columns") // 컬럼 전체조회
    public List<ColumnsResponseDto> getColums() {
        return columnsService.getColumns();
    }

    @GetMapping("/columns/{id}") // 컬럼 단건조회
    public ColumnsResponseDto getColumn(@PathVariable Long id) {
        return columnsService.getColumn(id);
    }

    @GetMapping("/myColumns") // 내 컬럼 전체조회
    public List<ColumnsResponseDto> myGetColums(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return columnsService.myGetColumns(userDetails.getUser());
    }

    @GetMapping("/myColumns/{id}") // 내 컬럼 단건조회
    public ColumnsResponseDto myGetColumn(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return columnsService.myGetColumn(id, userDetails.getUser());
    }

    @PatchMapping("/columns/{id}") // 컬럼 수정
    public ResponseEntity<CustomResponseEntity> updateColumn(@PathVariable Long id, @RequestBody ColumnsRequestDto columnsRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        columnsService.updateColumn(id, columnsRequestDto, userDetails.getUser());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_UPDATE_COLUMN);
    }

    @DeleteMapping("/columns/{id}") // 컬럼 삭제
    public ResponseEntity<CustomResponseEntity> deleteColumn(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        columnsService.removeColumn(id, userDetails.getUser());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_DELETE_COLUMN);
    }

    @PatchMapping("/columnseq/{id}") // 컬럼 순서 변경
    public ResponseEntity<CustomResponseEntity> sequenceChangeColumn(@PathVariable Long id, @RequestBody ColumnsSeqRequestDto columnsRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        columnsService.sequenceChangeColumn(id, columnsRequestDto, userDetails.getUser());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_CHANGE_COLUMN);
    }


}
