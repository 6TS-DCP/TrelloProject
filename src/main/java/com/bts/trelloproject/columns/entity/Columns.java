package com.bts.trelloproject.columns.entity;


import com.bts.trelloproject.board.entity.Boards;
import com.bts.trelloproject.card.entity.Card;
import com.bts.trelloproject.columns.dto.ColumnsRequestDto;
import com.bts.trelloproject.global.common.BaseLastModifiedTimeEntity;
import com.bts.trelloproject.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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
    private String columnName;

    @Column(nullable = false)
    private int columnSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    @JsonIgnore
    private Boards boards;

    @OneToMany(mappedBy = "columns", orphanRemoval = true)
    private List<Card> cardList = new ArrayList<>();


    //    @OneToMany (mappedBy = "boards", orphanRemoval = true)
//    private List<Cards> cardsList = new ArrayList<>();


    public Columns(Boards boards, ColumnsRequestDto columnRequestDto, User user) {
        this.columnName = columnRequestDto.getColumn_name();
        this.columnSeq = columnRequestDto.getColumn_seq();
        this.boards = boards;
        this.user = user;
    }

    public void update(ColumnsRequestDto columnsRequestDto) {
        this.columnName = columnsRequestDto.getColumn_name();
    }

    public void updateSeq(int seq) {
        this.columnSeq = seq;
    }

}
