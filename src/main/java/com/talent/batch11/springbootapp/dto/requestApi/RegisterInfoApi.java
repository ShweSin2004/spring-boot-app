package com.talent.batch11.springbootapp.dto.requestApi;

public record RegisterInfoApi(String name, String role, String email, String password, String confirmPassword,
                           String phoneNumber, String address) {

}
