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

    @Column(name = "inn")
    private String inn;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInn() {
        return inn;
    }

    public String getDirector() {
        return director;
    }

    @Column(name = "director")
    private String director;
}
