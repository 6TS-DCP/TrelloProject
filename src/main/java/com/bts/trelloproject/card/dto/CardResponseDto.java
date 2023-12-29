package com.bts.trelloproject.card.dto;

import com.bts.trelloproject.card.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDto {

    private Long id;
    private String cardName;
    private String cardColor;
    private String cardContent;
    private String cardWorker;
    private int cardSeq;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.cardName = card.getCardName();
        this.cardColor = card.getCardColor();
        this.cardContent = card.getCardContent();
        this.cardWorker = card.getCardWorker();
        this.cardSeq = card.getCardSeq();
    }
}
