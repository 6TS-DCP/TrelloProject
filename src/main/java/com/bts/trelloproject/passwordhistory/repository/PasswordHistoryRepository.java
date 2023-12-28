package com.bts.trelloproject.passwordhistory.repository;

import com.bts.trelloproject.passwordhistory.entity.PasswordHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

    List<PasswordHistory> findTop3ByUserIdOrderByCreatedTimeDesc(Long userId);
}
