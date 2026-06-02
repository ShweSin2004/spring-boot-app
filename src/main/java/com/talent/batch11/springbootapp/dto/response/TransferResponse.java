package com.talent.batch11.springbootapp.dto.response;

import lombok.Data;

@Data
public class TransferResponse {
    double amount;
    String message = "Transferred money successfully. ";
}
