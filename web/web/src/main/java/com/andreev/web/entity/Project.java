package com.andreev.web.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.*;;

@Getter
@Setter
public class Project{
    private int id;
    private String name;
    private String discription;
    private Date start;
    private Date end;
}