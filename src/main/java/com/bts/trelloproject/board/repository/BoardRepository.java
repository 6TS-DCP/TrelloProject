package com.bts.trelloproject.board.repository;

import com.bts.trelloproject.board.entity.Boards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Boards, Long> {
    Optional<List<Boards>> findByUser_Username(String username);
    List<Boards> findAllById(Long boardId);

    //Optional<Boards> findByUser_UserId(Long id);

    Boards findByIdAndUser_UserId(Long boardId, Long userId);

    //Boards findByBoardId(Long boardId);
}
