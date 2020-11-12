package com.sovback.sovback.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 10)
    private String role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String email;
    private String name;

}
