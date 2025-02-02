package com.example.demo.service;

import com.example.demo.model.BlogPost;
import com.example.demo.model.User;
import com.example.demo.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    @Autowired
    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    // Create a new blog post
    public BlogPost createPost(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }

    // Get a blog post by ID
    public Optional<BlogPost> getPostById(Long id) {
        return blogPostRepository.findById(id);
    }

    // Get all blog posts
    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    // Get posts by author
    public List<BlogPost> getPostsByAuthor(User author) {
        return blogPostRepository.findByAuthor(author);
    }

    // Search posts by title
    public List<BlogPost> searchPostsByTitle(String keyword) {
        return blogPostRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // Delete a blog post
    public void deletePost(Long id) {
        blogPostRepository.deleteById(id);
    }
}
