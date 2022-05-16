package com.greenfox.reddit_project.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;


//    private List <Post> posts;
    @OneToMany (mappedBy = "user",cascade = CascadeType.ALL)
    private List<Post> posts;

//    @OneToMany
//    private List<Vote> votes;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.posts = new ArrayList<>();
    }

    public User() {

    }
}
