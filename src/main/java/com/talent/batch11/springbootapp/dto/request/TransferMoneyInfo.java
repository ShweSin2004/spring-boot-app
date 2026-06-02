package com.talent.batch11.springbootapp.dto.request;

import lombok.Data;

@Data
public class TransferMoneyInfo {
    String receiver_phone;
    double amount;
    String password;
}
