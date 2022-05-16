package com.greenfox.reddit_project.services;

import com.greenfox.reddit_project.models.User;
import com.greenfox.reddit_project.models.Vote;
import org.springframework.stereotype.Service;

@Service
public interface VoteService {

    void registerVote(Long userId, Long postId, Integer value);

}
