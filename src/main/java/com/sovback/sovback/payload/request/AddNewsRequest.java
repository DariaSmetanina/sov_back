package com.sovback.sovback.payload.request;

import lombok.Data;

import java.io.File;

@Data
public class AddNewsRequest {
    private String title;
    private String importance;
    private String mainPart;
    private String text;
    private File file;
}
