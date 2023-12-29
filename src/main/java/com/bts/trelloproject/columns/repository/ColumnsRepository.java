package com.bts.trelloproject.columns.repository;

import com.bts.trelloproject.board.entity.Boards;
import com.bts.trelloproject.columns.entity.Columns;
import com.bts.trelloproject.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnsRepository extends JpaRepository<Columns, Long> {
    Optional<Columns> findByColumnSeqAndUser(int columnSeq, User user);
    Columns findByColumnSeq(int seq);
    List<Columns> findAllByUser(User user);

    Optional<Columns> findByIdAndUser(Long id, User user);

    List<Columns> findAllByUserAndBoards(User user, Boards boards);

    Columns findByIdAndBoards(Long columnId, Boards boards);
}
