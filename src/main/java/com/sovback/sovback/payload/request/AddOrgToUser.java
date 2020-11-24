package com.sovback.sovback.payload.request;

import lombok.Data;

@Data
public class AddOrgToUser {
    private String inn;
    private String username;
}
