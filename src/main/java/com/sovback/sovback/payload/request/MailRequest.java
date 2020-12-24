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

    public String getFrom() {
        return from;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo(){
        return new ArrayList<String>(Arrays.asList(to));
    }

}
