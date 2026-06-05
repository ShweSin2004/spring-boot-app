package com.talent.batch11.springbootapp.dto.requestApi;

public record TransferMoneyInfoApi(
    String receiver_phone,
    double amount,
    String password) {
}
