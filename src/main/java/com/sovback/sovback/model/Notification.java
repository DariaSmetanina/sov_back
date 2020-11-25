package com.sovback.sovback.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "notification")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_notification")
    private long id;

    @Column(name = "date")
    private String date;

    @Column(name = "importance")
    private String importance;

    @Column(name = "text")
    private String text;

    @Column(name = "organization")
    private long organization;

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getImportance() {
        return importance;
    }

    public String getText() {
        return text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOrganization(long organization) {
        this.organization = organization;
    }

    public long getOrganization() {
        return organization;
    }
}
