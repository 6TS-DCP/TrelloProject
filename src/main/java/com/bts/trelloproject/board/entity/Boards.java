package com.bts.trelloproject.board.entity;

import com.bts.trelloproject.board.dto.BoardRequestDto;
import com.bts.trelloproject.columns.entity.Columns;
import com.bts.trelloproject.global.common.BaseCreatedTimeEntity;
import com.bts.trelloproject.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "boards")
public class Boards extends BaseCreatedTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String color;

    @ManyToOne
    @JoinColumn (name = "user_name")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;

 /*  @OneToMany (mappedBy = "boards", orphanRemoval = true)
    private List<Columns> columnsList = new ArrayList<>();*/

    @OneToMany(mappedBy = "boards")
    @JsonIgnore
    private List<Columns> columnsList = new ArrayList<>();

//    @OneToMany (mappedBy = "boards", orphanRemoval = true)
//    private List<Cards> cardsList = new ArrayList<>();

    public Boards(BoardRequestDto dto, User user){
        this.user = user;
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.color = dto.getColor();
    }

    public void updateboard(BoardRequestDto dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.color = dto.getColor();
    }

}
