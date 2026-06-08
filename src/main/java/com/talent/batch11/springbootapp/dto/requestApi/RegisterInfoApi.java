package com.talent.batch11.springbootapp.dto.requestApi;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterInfoApi(
        @NotNull
        String name,

        String role,

        @NotNull
        String email,

        @NotNull
        @Size(max = 15)
        String password,

        @NotNull
        @Size(max = 15)
        String confirmPassword,

        @NotNull
        @Max(value = 11)
        String phoneNumber,

        String address) {

}
