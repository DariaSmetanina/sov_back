package com.sovback.sovback.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_account")
    private long id;

    @Column(name = "number")
    private String number;

    @Column(name = "date")
    private String date;

    @Column(name = "amount")
    private float amount;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "organization")
    private long organization;

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public float getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public long getOrganization() {
        return organization;
    }
}
