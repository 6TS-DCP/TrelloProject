package com.bts.trelloproject.columns.entity;


import com.bts.trelloproject.columns.dto.ColumnsRequestDto;
import com.bts.trelloproject.global.common.BaseLastModifiedTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity(name = "columns")
public class Columns extends BaseLastModifiedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String column_name;

    public Columns(ColumnsRequestDto columnRequestDto) {
        this.column_name = columnRequestDto.getColumn_name();
    }

    public void update(ColumnsRequestDto columnsRequestDto) {
        this.column_name = columnsRequestDto.getColumn_name();
    }

}
