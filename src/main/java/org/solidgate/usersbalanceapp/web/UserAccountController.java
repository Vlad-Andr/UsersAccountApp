package org.solidgate.usersbalanceapp.web;

import lombok.AllArgsConstructor;
import org.solidgate.usersbalanceapp.service.UserAccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/set-users-balance")
    public void setUsersBalance(@RequestBody Map<Integer, Integer> usersBalances) {
        userAccountService.updateUsersBalances(usersBalances);
    }
}
