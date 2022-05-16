package com.greenfox.reddit_project.services;

import com.greenfox.reddit_project.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    boolean userExists(String name);
    void addNewUser(User user);
    User getUserByName(String name);
    boolean loginVerification(String name, String password);

    User getUserById(Long id);

}
