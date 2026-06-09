package com.talent.batch11.springbootapp.dto.requestApi;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginInfoApi(
        @NotNull
        String email,

        @NotNull
        @Size(max = 15)
        String password) {
}
