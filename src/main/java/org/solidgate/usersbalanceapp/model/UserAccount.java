package org.solidgate.usersbalanceapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "user_account")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {

    @Id
    private Integer id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "account_balance", nullable = false)
    private Integer balance;
}
