package com.example.demo.repository;

import com.example.demo.model.BlogPost;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findByAuthor(User author);
    List<BlogPost> findByTitleContainingIgnoreCase(String title);
}
