package com.greenfox.reddit_project.services;

import com.greenfox.reddit_project.models.Post;
import com.greenfox.reddit_project.models.User;
import com.greenfox.reddit_project.repositories.PostPagingRepository;
import com.greenfox.reddit_project.repositories.PostRepository;
import com.greenfox.reddit_project.repositories.UserRepository;
import com.greenfox.reddit_project.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostUserVoteServiceImpl implements PostService, UserService, VoteService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    //paging repo

    private final PostPagingRepository postPagingRepository;

    @Autowired
    public PostUserVoteServiceImpl(PostRepository postRepository, UserRepository userRepository, VoteRepository voteRepository, PostPagingRepository postPagingRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
        this.postPagingRepository = postPagingRepository;
    }

    @Override
    public boolean verifyPassword(String passwordOne, String passwordTwo) {
        return passwordOne.equals(passwordTwo);
    }


    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getAllPostsSortedByScore() {
//        return postRepository.findAll().stream()
//                .sorted((p1,p2) -> p2.getScore().compareTo(p1.getScore()))
//                .collect(Collectors.toList());
        return postRepository.getTopBy();
    }

    @Override
    public void addNewPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public Post findPostById(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public void increaseScore(Long id) {
        findPostById(id).setScore(findPostById(id).getScore() + 1);
    }

    @Override
    public void decreaseScore(Long id) {
        findPostById(id).setScore(findPostById(id).getScore() - 1);
    }

    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void assignPostToUser(Post post, User user) {
        post.setUser(user);
    }


    // pagination bul***

    @Override
    public Page<Post> listPages(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("score").descending());
        return postPagingRepository.findAll(pageable);
    }

//    @Override
//    public List<Post> listPagesAndSortByScore(int pageNumber) {
//        Pageable pageable = PageRequest.of(pageNumber - 1,10);
//        return postPagingRepository.findAll(pageable).stream()
//                .sorted((p1,p2) -> p2.getScore().compareTo(p1.getScore())).collect(Collectors.toList());
//    }


    // user


    @Override
    public boolean userExists(String name) {
        return userRepository.findAll().stream().anyMatch(user -> user.getName().equals(name));
    }

    @Override
    public void addNewUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findUserByName(name).get();
    }

    @Override
    public boolean loginVerification(String name, String password) {
        return userRepository.findAll().stream()
                .anyMatch(user -> user.getName().equals(name) && user.getPassword().equals(password));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id).get();
    }



    // vote

    @Override
    public void registerVote(Long userId, Long postId, Integer value) {
        User user = getUserById(userId);
        Post post = findPostById(postId);

//        Vote vote = new Vote(value,post,user);
    }




}
