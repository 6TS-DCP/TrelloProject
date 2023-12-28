package com.bts.trelloproject.columns.dto;

import com.bts.trelloproject.columns.entity.Columns;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ColumnsResponseDto {
    private Long id;
    private String columnName;
    private int columnSeq;

    public ColumnsResponseDto(Columns columns) {
        this.id = columns.getId();
        this.columnName = columns.getColumnName();
        this.columnSeq = columns.getColumnSeq();
    }
}
