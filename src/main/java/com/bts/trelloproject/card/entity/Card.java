package com.bts.trelloproject.card.entity;

import com.bts.trelloproject.card.dto.CardRequestDto;
import com.bts.trelloproject.card.dto.CardUpdateRequestDto;
import com.bts.trelloproject.columns.entity.Columns;
import com.bts.trelloproject.comment.entity.Comment;
import com.bts.trelloproject.global.common.BaseLastModifiedTimeEntity;
import com.bts.trelloproject.user.entity.User;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "card")
public class Card extends BaseLastModifiedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String cardName;

    @Column(nullable = false)
    private String cardContent;

    @Column(nullable = false)
    private String cardColor;

    @Column(nullable = true)
    private String cardWorker;

    @Column(nullable = false)
    private int cardSeq;

    @Column(nullable = false)
    private int deadLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id", nullable = false)
    private Columns columns;

    @OneToMany(mappedBy = "card", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public Card(CardRequestDto cardRequestDto, User user, Columns columns) {
        this.cardName = cardRequestDto.getCardName();
        this.cardContent = cardRequestDto.getCardContent();
        this.cardColor = cardRequestDto.getCardColor();
        this.cardWorker = user.getUsername();
        this.cardSeq = cardRequestDto.getCardSeq();
        this.user = user;
        this.columns = columns;
        this.deadLine = 90;
    }

    public void update(CardUpdateRequestDto cardUpdateRequestDto) {
        this.cardName = cardUpdateRequestDto.getCardName();
        this.cardContent = cardUpdateRequestDto.getCardContent();
        this.cardColor = cardUpdateRequestDto.getCardColor();
        this.cardWorker = cardUpdateRequestDto.getCardWorker();
    }

    public void updateSeq(int seq) {
        this.cardSeq = seq;
    }

    public void updateDeadLine(int dead) {
        this.deadLine = dead;
    }
}
