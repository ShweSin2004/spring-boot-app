package com.talent.batch11.springbootapp.exception;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
public class CommonResponse<T> {

    HttpStatusCode httpStatusCode;
    String message;
    boolean success;
    T data;
    LocalDateTime systemDateTime;
    String apiName;
}
