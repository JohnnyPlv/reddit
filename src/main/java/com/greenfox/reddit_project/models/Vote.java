package com.greenfox.reddit_project.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "vote")
@Getter
@Setter
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    public Vote( Post post, User user) {
        this.id = id;
        this.post = post;
        this.user = user;
    }

    public Vote() {
    }
}
