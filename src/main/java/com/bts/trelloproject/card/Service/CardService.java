package com.bts.trelloproject.card.Service;

import com.bts.trelloproject.card.dto.CardRequestDto;
import com.bts.trelloproject.card.dto.CardResponseDto;
import com.bts.trelloproject.card.dto.CardSchedulerDto;
import com.bts.trelloproject.card.dto.CardSeqRequestDto;
import com.bts.trelloproject.card.dto.CardUpdateRequestDto;
import com.bts.trelloproject.card.entity.Card;
import com.bts.trelloproject.card.repository.CardRepository;
import com.bts.trelloproject.columns.entity.Columns;
import com.bts.trelloproject.columns.service.ColumnsService;
import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.exception.CustomException;
import com.bts.trelloproject.user.entity.User;
import com.bts.trelloproject.user.service.UserService;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserService userService;
    private final ColumnsService columnsService;

    public void createCard(Long columnId, CardRequestDto cardRequestDto, User user) {

        checkSeq(cardRequestDto.getCardSeq(), user);
        Columns columns = columnsService.findById(columnId);
        Card card = new Card(cardRequestDto, user, columns);
        cardRepository.save(card);
    }

    public List<CardResponseDto> getCard(User user) {

        User dbuser = userService.findById(user.getUserId());

        return cardRepository.findAllByUser(dbuser).stream().map(CardResponseDto::new).toList();
    }

    public CardResponseDto getCard(User user, Long columnId, Long cardId) {

        User dbuser = userService.findById(user.getUserId());

        Columns columns = columnsService.findById(columnId);

        Card card = cardRepository.findByIdAndUser(cardId, dbuser);

        return new CardResponseDto(card);
    }

    public void checkSeq(int columnSeq, User user) {
        Optional<Card> checkSeq = cardRepository.findByCardSeqAndUser(columnSeq, user);
        if(checkSeq.isPresent()) {
            throw new CustomException(StatusEnum.DUPLICATED_CARD_SEQ);
        }
    }

    public List<CardResponseDto> getMyCard(User user, Long columnId) {

        User dbuser = userService.findById(user.getUserId());
        Columns columns = columnsService.findById(columnId);

        return cardRepository.findAllByUser(dbuser).stream().map(CardResponseDto::new).toList();
    }

    public CardResponseDto getMyCard(User user, Long columnId, Long cardId) {

        User dbuser = userService.findById(user.getUserId());
        Columns columns = columnsService.findById(columnId);
        Card card = cardRepository.findByIdAndUser(cardId, dbuser);

        return new CardResponseDto(card);
    }

    @Transactional
    public void updateCard(Long columnId, Long id, CardUpdateRequestDto cardRequestDto, User user) {

        Card card = cardRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.CARD_NOT_FOUND));

        if (!card.getUser().getUsername().equals(user.getUsername())) {
            throw new CustomException(StatusEnum.CARD_NOT_MATCHED);
        }

        card.update(cardRequestDto);
    }

    @Transactional
    public void changeSchedule(Long cardId, CardSchedulerDto cardSchedulerDto, User user) {

        Card card = cardRepository.findByIdAndUser(cardId, user);

        card.updateDeadLine(cardSchedulerDto.getDate());
    }

    @Scheduled(cron = "* * 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void deleteCardWithScheduler() {
        List<Card> cardList = cardRepository.findAll();
        for (Card card : cardList){
            LocalDateTime startDT = card.getLastModifiedTime();
            LocalDateTime endDT = LocalDateTime.now();

            Period diff = Period.between(startDT.toLocalDate(), endDT.toLocalDate());
            if(diff.getDays() > card.getDeadLine()){
                cardRepository.delete(card);
            }
        }
    }

    @Transactional
    public void deleteCard(User user, Long id, Long columnId) {
        Card card = cardRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.CARD_NOT_FOUND));

        if (!card.getUser().getUsername().equals(user.getUsername())) {
            throw new CustomException(StatusEnum.CARD_NOT_MATCHED);
        }

        cardRepository.delete(card);
    }

    @Transactional
    public void sequenceChangeCard(Long id, Long columnId, CardSeqRequestDto cardSeqRequestDto, User user) {

        Card card = cardRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.CARD_NOT_FOUND));

        if(changeCard(cardSeqRequestDto.getCardSeq(), user)) {
            Card changeSeqCard = cardRepository.findByCardSeq(cardSeqRequestDto.getCardSeq());
            changeSeqCard.updateSeq(card.getCardSeq());
        }

        card.updateSeq(cardSeqRequestDto.getCardSeq());
    }

    @Transactional
    public boolean changeCard(int cardSeq, User user) {
        Optional<Card> checkSeq = cardRepository.findByCardSeqAndUser(cardSeq, user);
        if(checkSeq.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public Card findById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new CustomException(StatusEnum.CARD_NOT_FOUND));
    }

}
