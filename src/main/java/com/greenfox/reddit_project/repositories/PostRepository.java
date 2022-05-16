package com.greenfox.reddit_project.repositories;

import com.greenfox.reddit_project.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query(value = "SELECT * FROM post ORDER BY score DESC LIMIT 10" , nativeQuery=true)
    List<Post> getTopBy();


}
