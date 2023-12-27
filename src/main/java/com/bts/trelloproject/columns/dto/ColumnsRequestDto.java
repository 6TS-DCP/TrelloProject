package com.bts.trelloproject.columns.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class ColumnsRequestDto {

    private String column_name;

    public ColumnsRequestDto(String column_name) {
        this.column_name = column_name;
    }
}
