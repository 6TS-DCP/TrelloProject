package com.bts.trelloproject.passwordhistory.service;

import com.bts.trelloproject.passwordhistory.entity.PasswordHistory;
import com.bts.trelloproject.passwordhistory.repository.PasswordHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordHistoryService {

    private final PasswordHistoryRepository passwordHistoryRepository;

    public void savePasswordHistory(PasswordHistory passwordHistory) {

        passwordHistoryRepository.save(passwordHistory);
    }

    public List<PasswordHistory> findTop3PasswordHistory(Long userId) {

        return passwordHistoryRepository.findTop3ByUserIdOrderByCreatedTimeDesc(userId);
    }
}