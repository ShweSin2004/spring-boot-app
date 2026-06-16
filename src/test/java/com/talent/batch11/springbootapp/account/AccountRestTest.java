package com.talent.batch11.springbootapp.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@SpringBootTest
public class AccountRestTest {

    String token;

    @Test
    void register() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9999/account/registration"))
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

    @BeforeEach
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
            token = response.headers().firstValue("access-token").orElse("");
            System.out.println("Access-token: " + token);
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
                    .uri(URI.create("http://localhost:9999/account/deposits"))
                    .header("apikey", "zuTG5ioRPx75sOderkUMGuDnepg8WD4z6jKD4ClPktHUWDlT")
                    .header("authorization", token)
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
                    .uri(URI.create("http://localhost:9999/account/withdrawal"))
                    .header("apikey", "zuTG5ioRPx75sOderkUMGuDnepg8WD4z6jKD4ClPktHUWDlT")
                    .header("authorization", token)
                    .header("content-type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\n  \"amount\": -400\n}"))
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
                    .uri(URI.create("http://localhost:9999/account/topup"))
                    .header("apikey", "zuTG5ioRPx75sOderkUMGuDnepg8WD4z6jKD4ClPktHUWDlT")
                    .header("authorization", token)
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
                    .uri(URI.create("http://localhost:9999/account/transfers"))
                    .header("apikey", "zuTG5ioRPx75sOderkUMGuDnepg8WD4z6jKD4ClPktHUWDlT")
                    .header("authorization", token)
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
                    .header("authorization", token)
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
