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
    private String column_name;

    public ColumnsResponseDto(Columns columns) {
        this.id = columns.getId();
        this.column_name = columns.getColumn_name();
    }
}
