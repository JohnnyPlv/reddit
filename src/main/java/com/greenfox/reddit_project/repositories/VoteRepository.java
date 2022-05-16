package com.greenfox.reddit_project.repositories;

import com.greenfox.reddit_project.models.User;
import com.greenfox.reddit_project.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {

}
