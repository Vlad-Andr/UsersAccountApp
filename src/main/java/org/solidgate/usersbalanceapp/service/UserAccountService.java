package org.solidgate.usersbalanceapp.service;

import lombok.AllArgsConstructor;
import org.solidgate.usersbalanceapp.model.UserAccount;
import org.solidgate.usersbalanceapp.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class UserAccountService {

    private UserAccountRepository userAccountRepository;

    public void updateUsersBalances(Map<Integer, Integer> usersBalances) {
        usersBalances.forEach((userId, balance) -> {
            UserAccount userAccount = new UserAccount();
            userAccount.setId(userId);
            userAccount.setBalance(balance);
            userAccountRepository.save(userAccount);
        });
    }
}
