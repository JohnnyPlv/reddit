package com.greenfox.reddit_project;

import com.greenfox.reddit_project.models.Post;
import com.greenfox.reddit_project.models.User;
import com.greenfox.reddit_project.services.PostService;
import com.greenfox.reddit_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedditProjectApplication implements CommandLineRunner {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public RedditProjectApplication(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(RedditProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User("lord","psw");

        userService.addNewUser(user1);

        postService.addNewPost(new Post("test1","test1", 0,user1));
        postService.addNewPost(new Post("test2","test1", 0,user1));
        postService.addNewPost(new Post("test3","test1", 0,user1));

    }
}
