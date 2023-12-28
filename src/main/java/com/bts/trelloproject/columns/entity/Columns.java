package com.bts.trelloproject.columns.entity;


import com.bts.trelloproject.columns.dto.ColumnsRequestDto;
import com.bts.trelloproject.global.common.BaseLastModifiedTimeEntity;
import com.bts.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity(name = "columns")
public class Columns extends BaseLastModifiedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String columnName;

    @Column(nullable = false)
    private int columnSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Columns(ColumnsRequestDto columnRequestDto, User user) {
        this.columnName = columnRequestDto.getColumn_name();
        this.columnSeq = columnRequestDto.getColumn_seq();
        this.user = user;
    }

    public void update(ColumnsRequestDto columnsRequestDto) {
        this.columnName = columnsRequestDto.getColumn_name();
    }

    public void updateSeq(int seq) {
        this.columnSeq = seq;
    }

}
