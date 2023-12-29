package com.bts.trelloproject.card.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardRequestDto {

    private String cardName;
    private String cardContent;
    private String cardColor;
    private int cardSeq;

    public CardRequestDto(String cardName, String cardContent, String cardColor, int cardSeq) {
        this.cardName = cardName;
        this.cardContent = cardContent;
        this.cardColor = cardColor;
        this.cardSeq = cardSeq;
    }

}
