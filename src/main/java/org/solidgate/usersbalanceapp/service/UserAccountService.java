package org.solidgate.usersbalanceapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.solidgate.usersbalanceapp.model.UserAccount;
import org.solidgate.usersbalanceapp.repository.UserAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Transactional
    public void updateUsersBalances(Map<Integer, Integer> usersBalances) {
        int batchSize = 100_000;
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < usersBalances.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, usersBalances.size());
            Map<Integer, Integer> batch = usersBalances.entrySet().stream()
                    .skip(i)
                    .limit(endIndex - i)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            executorService.submit(() -> processBatch(batch));
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            log.info("Waiting for executor service to finish...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processBatch(Map<Integer, Integer> batch) {
        userAccountRepository.saveAll(batch.entrySet().stream()
                .map(entry ->
                        UserAccount.builder()
                                .id(entry.getKey())
                                .balance(entry.getValue())
                                .build())
                .toList());
    }
}
