package com.bts.trelloproject.card.controller;

import com.bts.trelloproject.card.Service.CardService;
import com.bts.trelloproject.card.dto.CardRequestDto;
import com.bts.trelloproject.card.dto.CardResponseDto;
import com.bts.trelloproject.card.dto.CardSchedulerDto;
import com.bts.trelloproject.card.dto.CardSeqRequestDto;
import com.bts.trelloproject.card.dto.CardUpdateRequestDto;
import com.bts.trelloproject.global.common.CustomResponseEntity;
import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.security.userdetails.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/columns/{columnId}/cards") // 카드 생성
    public ResponseEntity<CustomResponseEntity> createCard(@PathVariable Long columnId,
            @RequestBody CardRequestDto cardRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        cardService.createCard(columnId, cardRequestDto, userDetails.getUser());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_CREATE_CARD);
    }

    @GetMapping("/cards") // 카드 전체조회
    public List<CardResponseDto> getCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long columnId) {

        return cardService.getCard(userDetails.getUser());
    }

    @GetMapping("/cards/{cardId}") // 카드 단건조회
    public CardResponseDto getCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long columnId, @PathVariable Long cardId) {

        return cardService.getCard(userDetails.getUser(), columnId, cardId);
    }

    @GetMapping("/columns/{columnId}/myCards") // 내 카드 전체조회
    public List<CardResponseDto> getMyCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long columnId) {

        return cardService.getMyCard(userDetails.getUser(),columnId);
    }

    @GetMapping("/columns/{columnId}/myCards/{cardId}") // 내 카드 단건조회
    public CardResponseDto getMyCard(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long columnId) {

        return cardService.getMyCard(userDetails.getUser(),columnId, cardId);
    }

    @PatchMapping("/columns/{columnId}/cards/{id}") // 카드 수정
    public ResponseEntity<CustomResponseEntity> updateCard(@PathVariable Long columnId, @PathVariable Long id,
            @RequestBody CardUpdateRequestDto cardRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        cardService.updateCard(columnId, id, cardRequestDto, userDetails.getUser());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_UPDATE_CARD);
    }

    @DeleteMapping("/columns/{columnId}/cards/{id}") // 카드 삭제
    public ResponseEntity<CustomResponseEntity> deleteCard(@PathVariable Long columnId,
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {

        cardService.deleteCard(userDetails.getUser(), id, columnId);
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_DELETE_CARD);
    }

    @PatchMapping("/columns/{columnId}/cardseq/{id}") // 카드 순서 변경
    public ResponseEntity<CustomResponseEntity> sequenceChangeColumn(@PathVariable Long id,
            @RequestBody CardSeqRequestDto cardSeqRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long columnId) {

        cardService.sequenceChangeCard(id, columnId, cardSeqRequestDto, userDetails.getUser());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_CHANGE_CARD);
    }

    @PatchMapping("/{cardId}") // 카드 마감일 변경
    public ResponseEntity<CustomResponseEntity> changeSchedule(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CardSchedulerDto cardSchedulerDto, @PathVariable Long cardId) {

        cardService.changeSchedule(cardId, cardSchedulerDto, userDetails.getUser());
        return CustomResponseEntity.toResponseEntity(StatusEnum.SUCCESS_CHANGE_DEADLINE);
    }

}
