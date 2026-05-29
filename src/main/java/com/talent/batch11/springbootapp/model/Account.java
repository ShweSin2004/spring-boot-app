package com.talent.batch11.springbootapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Data
public class Account extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String password;
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    private double balance;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL
            , orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

}