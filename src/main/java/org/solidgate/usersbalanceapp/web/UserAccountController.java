package org.solidgate.usersbalanceapp.web;

import lombok.AllArgsConstructor;
import org.solidgate.usersbalanceapp.service.UserAccountService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping(value = "/set-users-balance", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setUsersBalance(@RequestBody Map<Integer, Integer> usersBalances) {
        userAccountService.updateUsersBalances(usersBalances);
        return ResponseEntity.ok().build();
    }
}
