package com.Rajasekhar.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String category;

    @ElementCollection
    private List<String> tags=ArrayList<>();

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private Chat chat;


    @ManyToOne
    private User  user;


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private  List<Issue> issues=new ArrayList<>();

    @ManyToMany
    private List<User> team=new ArrayList<>();

}
