package com.sovback.sovback.payload.request;

import lombok.Data;

@Data
public class AddOrg {
    private String name;
    private String inn;
    private String director;
}
