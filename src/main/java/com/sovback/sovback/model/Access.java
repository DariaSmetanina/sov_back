package com.sovback.sovback.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "access")
@Data
public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_access")
    private long id;

    @Column(name = "user")
    private long user;

    @Column(name = "organization")
    private long organization;

    public long getOrganization() {
        return organization;
    }
}
