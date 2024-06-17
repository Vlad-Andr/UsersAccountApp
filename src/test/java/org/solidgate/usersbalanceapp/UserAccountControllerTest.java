package org.solidgate.usersbalanceapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.solidgate.usersbalanceapp.model.UserAccount;
import org.solidgate.usersbalanceapp.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @AfterEach
    public void clearDbAfterEachTest() {
        userAccountRepository.deleteAll();
    }

    @Test
    public void testUpdateUsersBalancesOnSmallData_OK() throws Exception {
        Map<Integer, Integer> usersBalances = Map.of(1, 100, 2, 200);
        String jsonRequest = objectMapper.writeValueAsString(usersBalances);

        mockMvc.perform(post("/user/set-users-balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        Optional<UserAccount> user1 = userAccountRepository.findById(1);
        Optional<UserAccount> user2 = userAccountRepository.findById(2);
        assertTrue(user1.isPresent());
        assertEquals(100, user1.get().getBalance());
        assertTrue(user2.isPresent());
        assertEquals(200, user2.get().getBalance());
    }

    @Test
    @Disabled
    public void testUpdateUsersBalancesOnBigData_OK() throws Exception {
        int before = LocalDateTime.now().getSecond();
        Map<Integer, Integer> usersBalances = IntStream.rangeClosed(1, 1_000_000)
                .boxed()
                .collect(Collectors.toMap(i -> i, i -> i * 20));

        String jsonPayload = objectMapper.writeValueAsString(usersBalances);

        mockMvc.perform(post("/user/set-users-balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
        int after = LocalDateTime.now().getSecond();
        log.info("TIME TAKEN: {} SECONDS", after - before);
    }

    @Test
    public void testEmptyInputData_OK() throws Exception {
        Map<Integer, Integer> usersBalances = Collections.emptyMap();

        String jsonRequest = objectMapper.writeValueAsString(usersBalances);

        mockMvc.perform(post("/user/set-users-balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        Optional<UserAccount> user = userAccountRepository.findById(1);
        assertFalse(user.isPresent());
    }

    @Test
    public void testInvalidInputData_NOT_OK() throws Exception {
        String corruptedJsonRequest = "{\"1\": \"wrong data type\"}";

        mockMvc.perform(post("/user/set-users-balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(corruptedJsonRequest))
                .andExpect(status().isBadRequest());
    }
}
