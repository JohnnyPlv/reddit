package com.greenfox.reddit_project.repositories;

import com.greenfox.reddit_project.models.Post;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPagingRepository extends PagingAndSortingRepository<Post,Long> {

}
