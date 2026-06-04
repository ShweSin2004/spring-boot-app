package com.talent.batch11.springbootapp.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.talent.batch11.springbootapp.model.enumDemo.TransactionType;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "transaction")
public class Transaction extends AbstractEntity{


    @ToString.Exclude
    @ManyToOne()
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private double amount;
    private double previous_amount;

}