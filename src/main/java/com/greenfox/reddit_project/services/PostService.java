package com.greenfox.reddit_project.services;

import com.greenfox.reddit_project.models.Post;
import com.greenfox.reddit_project.models.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public interface PostService {

    // login

    boolean verifyPassword(String passwordOne, String passwordTwo);


    List<Post> getAllPosts();
    List<Post> getAllPostsSortedByScore();
    void addNewPost(Post post);
    Post findPostById(Long id);
    void increaseScore(Long id);
    void decreaseScore(Long id);
    void updatePost(Post post);
    void assignPostToUser(Post post,User user);


    // paging

    Page<Post> listPages( Integer pageNumber);

//    List<Post> listPagesAndSortByScore( int pageNumber);


}
