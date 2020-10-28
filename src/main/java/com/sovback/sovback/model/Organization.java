package com.sovback.sovback.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "organization")
@Data
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_organization")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "INN_KPP")
    private String INN_KPP;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getINN_KPP() {
        return INN_KPP;
    }

    public String getDirector() {
        return director;
    }

    @Column(name = "director")
    private String director;
}
