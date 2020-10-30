package com.sovback.sovback.payload.request;

import lombok.Data;

@Data
public class AddNewsRequest {
    private String title;
    private String importance;
    private String mainPart;
    private String text;
}
