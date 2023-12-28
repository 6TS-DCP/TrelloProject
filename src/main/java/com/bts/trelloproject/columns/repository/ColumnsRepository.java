package com.bts.trelloproject.columns.repository;

import com.bts.trelloproject.columns.dto.ColumnsResponseDto;
import com.bts.trelloproject.columns.entity.Columns;
import com.bts.trelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ColumnsRepository extends JpaRepository<Columns, Long> {
    Optional<Columns> findByColumnSeqAndUser(int columnSeq, User user);
    Columns findByColumnSeq(int seq);
    List<Columns> findAllByUser(User user);

    Optional<Columns> findByIdAndUser(Long id, User user);
}
