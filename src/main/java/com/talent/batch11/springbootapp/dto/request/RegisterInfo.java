package com.talent.batch11.springbootapp.dto.request;

import lombok.Data;

@Data
public class RegisterInfo {
    String name;
    String role;
    String email;
    String password;
    String confirmPassword;
    String phoneNumber;
    String address;
}
