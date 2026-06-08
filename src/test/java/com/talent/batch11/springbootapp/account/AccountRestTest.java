package com.talent.batch11.springbootapp.account;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest
public class AccountRestTest {

    @Test
    void register() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9999/account/register"))
                    .header("apikey", "zuTG5ioRPx75sOderkUMGuDnepg8WD4z6jKD4ClPktHUWDlT")
                    .header("content-type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\n  \"name\": \"San\",\n  \"email\": \"san@gmail.com\",\n  \"password\": \"1234\",\n  \"phoneNumber\": \"096633777\",\n  \"address\": \"Ygn\"\n}"))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void login () {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9999/account/login"))
                    .header("apikey", "zuTG5ioRPx75sOderkUMGuDnepg8WD4z6jKD4ClPktHUWDlT")
                    .header("content-type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\n  \"email\": \"sin@gmail.com\",\n  \"password\": \"1234\"\n}"))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deposit() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9999/account/deposit"))
                    .header("apikey", "zuTG5ioRPx75sOderkUMGuDnepg8WD4z6jKD4ClPktHUWDlT")
                    .header("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaW5AZ21haWwuY29tIiwibmFtZSI6IlNpbiIsImFjY291bnRfaWQiOjMsImVtYWlsIjoic2luQGdtYWlsLmNvbSIsIlJPTEUiOiJVc2VyIiwiaWF0IjoxNzgwOTA2ODU3LCJleHAiOjE3ODE3NzA4NTd9.9osS2-DnG2FB0RdOwbxXy5ykOMMj-avRKF5wELTYdjQKjfeEW5rPqbLLhxaUznt_9ft9_iTOOFqAF6GaLnUl8Q")
                    .header("content-type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\n  \"amount\": 400\n}"))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void withdraw() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9999/account/withdraw"))
                    .header("apikey", "zuTG5ioRPx75sOderkUMGuDnepg8WD4z6jKD4ClPktHUWDlT")
                    .header("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaW5AZ21haWwuY29tIiwibmFtZSI6IlNpbiIsImFjY291bnRfaWQiOjMsImVtYWlsIjoic2luQGdtYWlsLmNvbSIsIlJPTEUiOiJVc2VyIiwiaWF0IjoxNzgwOTA2ODU3LCJleHAiOjE3ODE3NzA4NTd9.9osS2-DnG2FB0RdOwbxXy5ykOMMj-avRKF5wELTYdjQKjfeEW5rPqbLLhxaUznt_9ft9_iTOOFqAF6GaLnUl8Q")
                    .header("content-type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\n  \"amount\": 400\n}"))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void topup() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9999/account/withdraw"))
                    .header("apikey", "zuTG5ioRPx75sOderkUMGuDnepg8WD4z6jKD4ClPktHUWDlT")
                    .header("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaW5AZ21haWwuY29tIiwibmFtZSI6IlNpbiIsImFjY291bnRfaWQiOjMsImVtYWlsIjoic2luQGdtYWlsLmNvbSIsIlJPTEUiOiJVc2VyIiwiaWF0IjoxNzgwOTA2ODU3LCJleHAiOjE3ODE3NzA4NTd9.9osS2-DnG2FB0RdOwbxXy5ykOMMj-avRKF5wELTYdjQKjfeEW5rPqbLLhxaUznt_9ft9_iTOOFqAF6GaLnUl8Q")
                    .header("content-type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\n  \"amount\": 400\n}"))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void transfer() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9999/account/transfer"))
                    .header("apikey", "zuTG5ioRPx75sOderkUMGuDnepg8WD4z6jKD4ClPktHUWDlT")
                    .header("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaW5AZ21haWwuY29tIiwibmFtZSI6IlNpbiIsImFjY291bnRfaWQiOjMsImVtYWlsIjoic2luQGdtYWlsLmNvbSIsIlJPTEUiOiJVc2VyIiwiaWF0IjoxNzgwOTA2ODU3LCJleHAiOjE3ODE3NzA4NTd9.9osS2-DnG2FB0RdOwbxXy5ykOMMj-avRKF5wELTYdjQKjfeEW5rPqbLLhxaUznt_9ft9_iTOOFqAF6GaLnUl8Q")
                    .header("content-type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\n  \"receiver_phone\": \"09333222111\",\n  \"amount\": 500,\n  \"password\": \"1234\"\n}"))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAccInfo() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9999/account/"))
                    .header("apikey", "zuTG5ioRPx75sOderkUMGuDnepg8WD4z6jKD4ClPktHUWDlT")
                    .header("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaW5AZ21haWwuY29tIiwibmFtZSI6IlNpbiIsImFjY291bnRfaWQiOjMsImVtYWlsIjoic2luQGdtYWlsLmNvbSIsIlJPTEUiOiJVc2VyIiwiaWF0IjoxNzgwOTA2ODU3LCJleHAiOjE3ODE3NzA4NTd9.9osS2-DnG2FB0RdOwbxXy5ykOMMj-avRKF5wELTYdjQKjfeEW5rPqbLLhxaUznt_9ft9_iTOOFqAF6GaLnUl8Q")
                    .header("content-type", "application/json")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkHistory() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9999/account/checkhistory"))
                    .header("apikey", "zuTG5ioRPx75sOderkUMGuDnepg8WD4z6jKD4ClPktHUWDlT")
                    .header("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaW5AZ21haWwuY29tIiwibmFtZSI6IlNpbiIsImFjY291bnRfaWQiOjMsImVtYWlsIjoic2luQGdtYWlsLmNvbSIsIlJPTEUiOiJVc2VyIiwiaWF0IjoxNzgwOTA2ODU3LCJleHAiOjE3ODE3NzA4NTd9.9osS2-DnG2FB0RdOwbxXy5ykOMMj-avRKF5wELTYdjQKjfeEW5rPqbLLhxaUznt_9ft9_iTOOFqAF6GaLnUl8Q")
                    .header("content-type", "application/json")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
