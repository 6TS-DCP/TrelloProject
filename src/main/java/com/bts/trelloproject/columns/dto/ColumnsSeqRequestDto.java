package com.bts.trelloproject.columns.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ColumnsSeqRequestDto {
    private int columnSeq;

    public ColumnsSeqRequestDto(int column_seq) {
        this.columnSeq = column_seq;
    }
}
