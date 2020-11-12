package com.sovback.sovback.payload.request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
        private String username;
        private String password;

}
