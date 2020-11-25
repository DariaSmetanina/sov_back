package com.sovback.sovback.payload.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class MailRequest {

    private String from;
    private String[] to = null;
    private String importance;
    private String subject;
    private String text;

    public List<String> getTo(){
        return new ArrayList<String>(Arrays.asList(to));
    }

}
