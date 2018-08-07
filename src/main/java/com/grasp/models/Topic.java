package com.grasp.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Topic {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3, max = 25)
    private String name;

  //  @OneToMany
 //   @JoinColumn(name="topic_id")
  //  private List<Result> results =new ArrayList<>();

    public Topic() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
