package org.solidgate.usersbalanceapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solidgate.usersbalanceapp.model.UserAccount;
import org.solidgate.usersbalanceapp.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    @Value("${batch.size}")
    private int batchSize;
    private static final Logger logger = LoggerFactory.getLogger(UserAccountService.class);

    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional
    public void updateUsersBalances(Map<Integer, Integer> usersBalances) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < usersBalances.size(); i += batchSize) {
            processBatch(usersBalances, i, executorService);
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {
            logger.info("Waiting to finish updating balances...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processBatch(Map<Integer, Integer> usersBalances, int i, ExecutorService executorService) {
        int lastIndex = Math.min(i + batchSize, usersBalances.size());
        Map<Integer, Integer> batch = usersBalances.entrySet().stream()
                .skip(i)
                .limit(lastIndex - i)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        executorService.submit(() -> processBatch(batch));
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
