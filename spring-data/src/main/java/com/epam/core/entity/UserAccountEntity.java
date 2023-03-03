package com.epam.core.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_accounts")
public class UserAccountEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private UserEntity user;
    @Column
    private int balance;
}
