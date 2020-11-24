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

    public void setUser(long user) {
        this.user = user;
    }

    public void setOrganization(long organization) {
        this.organization = organization;
    }

    @Column(name = "organization")
    private long organization;

    public long getOrganization() {
        return organization;
    }
}
