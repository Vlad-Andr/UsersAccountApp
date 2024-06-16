package org.solidgate.usersbalanceapp.repository;

import org.solidgate.usersbalanceapp.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
}
