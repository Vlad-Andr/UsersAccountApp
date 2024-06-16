package org.solidgate.usersbalanceapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_account")
public class UserAccount {

    @Id
    private Integer id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "account_balance", nullable = false)
    private Integer balance;
}
