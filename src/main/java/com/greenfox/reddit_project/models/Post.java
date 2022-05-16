package com.greenfox.reddit_project.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name="post")
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;

    private Integer score;

    @ManyToOne
    private User user;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfCreation;


    public Post(String title, String url, Integer score, User user) {
        this.title = title;
        this.url = url;
        this.score = score;
        this.dateOfCreation = new Date();
        this.user = user;
    }

    public Post() {
        this.score = 0;
        this.dateOfCreation = new Date();
    }
}
