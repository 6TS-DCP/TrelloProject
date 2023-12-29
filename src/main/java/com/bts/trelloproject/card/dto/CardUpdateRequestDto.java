package com.bts.trelloproject.card.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardUpdateRequestDto {
    private String cardName;
    private String cardContent;
    private String cardColor;
    private String cardWorker;

    public CardUpdateRequestDto(String cardName, String cardContent, String cardColor, String cardWorker) {
        this.cardName = cardName;
        this.cardContent = cardContent;
        this.cardColor = cardColor;
        this.cardWorker = cardWorker;
    }
}
