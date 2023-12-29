package com.bts.trelloproject.card.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardSeqRequestDto {
    private int cardSeq;

    public CardSeqRequestDto(int cardSeq) {
        this.cardSeq = cardSeq;
    }
}
