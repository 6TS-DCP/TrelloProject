package com.bts.trelloproject.card.repository;

import com.bts.trelloproject.card.entity.Card;
import com.bts.trelloproject.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByUser(User dbuser);

    Optional<Card> findByCardSeqAndUser(int columnSeq, User user);

    Card findByIdAndUser(Long cardId, User user);

    Card findByCardSeq(int cardSeq);
}
