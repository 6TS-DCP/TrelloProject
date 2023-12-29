package com.bts.trelloproject.columns.controller;

import com.bts.trelloproject.columns.dto.ColumnsRequestDto;
import com.bts.trelloproject.columns.dto.ColumnsResponseDto;
import com.bts.trelloproject.columns.dto.ColumnsSeqRequestDto;
import com.bts.trelloproject.columns.service.ColumnsService;
import com.bts.trelloproject.global.common.CustomResponseEntity;
import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.security.userdetails.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ColumnsController {

    private final ColumnsService columnsService;

    @PostMapping("/board/{boardId}/columns") // 컬럼 생성
    public ResponseEntity<CustomResponseEntity> createColumn(@RequestBody ColumnsRequestDto columnsRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId) {

        columnsService.createColumns(boardId, columnsRequestDto, userDetails.getUser());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_CREATE_COLUMN);
    }

    @GetMapping("/columns") // 컬럼 전체조회
    public List<ColumnsResponseDto> getColumns() {

        return columnsService.getColumns();
    }

    @GetMapping("/columns/{columnId}") // 컬럼 단건조회
    public ColumnsResponseDto getColumn(@PathVariable Long columnId) {

        return columnsService.getColumn(columnId);
    }

    @GetMapping("/board/{boardId}/myColumns") // 내 컬럼 전체조회
    public List<ColumnsResponseDto> getMyColumns(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long boardId) {

        return columnsService.getMyColumns(boardId, userDetails.getUser());
    }

    @GetMapping("/board/{boardId}/myColumns/{columnId}") // 내 컬럼 단건조회
    public ColumnsResponseDto getMyColumn(@PathVariable Long boardId, @PathVariable Long columnId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return columnsService.getMyColumn(boardId, columnId, userDetails.getUser());
    }

    @PatchMapping("/board/{boardId}/columns/{columnId}") // 컬럼 수정
    public ResponseEntity<CustomResponseEntity> updateColumn(@PathVariable Long columnId, @RequestBody ColumnsRequestDto columnsRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long boardId) {

        columnsService.updateColumn(boardId, columnId, columnsRequestDto, userDetails.getUser());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_UPDATE_COLUMN);
    }

    @DeleteMapping("/board/{boardId}/columns/{columnId}") // 컬럼 삭제
    public ResponseEntity<CustomResponseEntity> deleteColumn(@PathVariable Long columnId, @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long boardId) {

        columnsService.deleteColumn(boardId, columnId, userDetails.getUser());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_DELETE_COLUMN);
    }

    @PatchMapping("/board/{boardId}/columnseq/{columnId}") // 컬럼 순서 변경
    public ResponseEntity<CustomResponseEntity> sequenceChangeColumn(@PathVariable Long columnId, @RequestBody ColumnsSeqRequestDto columnsRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long boardId) {

        columnsService.sequenceChangeColumn(columnId, columnsRequestDto, userDetails.getUser());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_CHANGE_COLUMN);
    }


}
