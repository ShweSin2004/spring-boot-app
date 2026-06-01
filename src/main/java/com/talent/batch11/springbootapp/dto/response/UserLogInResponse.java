package com.talent.batch11.springbootapp.dto.response;

import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.model.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class UserLogInResponse {
    Account account;
}
