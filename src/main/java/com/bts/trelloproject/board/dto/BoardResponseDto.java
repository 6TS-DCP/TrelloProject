package com.bts.trelloproject.board.dto;

import com.bts.trelloproject.board.entity.Boards;
import com.bts.trelloproject.columns.dto.ColumnsResponseDto;
import com.bts.trelloproject.columns.entity.Columns;
import com.bts.trelloproject.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardResponseDto{

    private long id;
    private String title;
    private String content;
    private String user;
    private List<ColumnsResponseDto> columnsResponseDtoListList;

    public BoardResponseDto(Boards boards) {
        this.id = boards.getId();
        this.title = boards.getTitle();
        this.content = boards.getContent();
        this.user = boards.getUser().getUsername();
        /*this.columnsResponseDtoListList = columnsListToDtoList(boards);*/
    }

 /*   public List<ColumnsResponseDto> columnsListToDtoList(Boards boards) {
        List<ColumnsResponseDto> columnsResponseDtoList = new ArrayList<>();
        List<Columns> columnsList = new ArrayList<>();
        //컬럼 리스트 반환
        columnsList = boards.getColumnsList();
        //컬럼 리스트를 columnsDto리스트로 바꾸기
        columnsList.forEach(columns -> {
            var columnsDto = new ColumnsResponseDto(columns);
            columnsResponseDtoList.add(columnsDto);
        });
        return columnsResponseDtoList;
    }*/
}
