package com.sovback.sovback.payload.request;

import lombok.Data;

@Data
public class userSettingsRequest {

    private String email;
    private String oldPassword;
    private String newPassword;
    private String newPasswordCopy;
}
