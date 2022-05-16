package com.greenfox.reddit_project.repositories;

import com.greenfox.reddit_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByName(String name);
    Optional<User> findUserById(Long id);
}
