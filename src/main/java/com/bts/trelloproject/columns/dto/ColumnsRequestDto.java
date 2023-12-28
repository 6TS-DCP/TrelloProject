package com.bts.trelloproject.columns.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class ColumnsRequestDto {

    private String column_name;
    private int column_seq;

    public ColumnsRequestDto(String column_name, int column_seq) {
        this.column_name = column_name;
        this.column_seq = column_seq;
    }
}
