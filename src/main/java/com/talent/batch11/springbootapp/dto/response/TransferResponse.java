package com.talent.batch11.springbootapp.dto.response;

import com.talent.batch11.springbootapp.model.Account;
import lombok.Data;

@Data
public class TransferResponse {
    Account sender;
    Account receiver;
}
