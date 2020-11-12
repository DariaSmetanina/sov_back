package com.sovback.sovback.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String name;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username,
                       List<String> roles, String name, String email) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.name = name;
        this.email = email;
    }
}
