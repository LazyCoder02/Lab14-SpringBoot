package com.example.demo.controller;

import com.example.demo.model.BlogPost;
import com.example.demo.model.User;
import com.example.demo.service.BlogPostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {
    private final BlogPostService blogPostService;
    private final UserService userService;

    @Autowired
    public BlogPostController(BlogPostService blogPostService, UserService userService) {
        this.blogPostService = blogPostService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestParam Long userId, @RequestBody BlogPost blogPost) {
        Optional<User> author = userService.getUserById(userId);
        if (author.isPresent()) {
            blogPost.setAuthor(author.get());
            return ResponseEntity.ok(blogPostService.createPost(blogPost));
        }
        return ResponseEntity.badRequest().body("Invalid user ID");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getPostById(@PathVariable Long id) {
        Optional<BlogPost> blogPost = blogPostService.getPostById(id);
        return blogPost.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/search")
    public ResponseEntity<List<BlogPost>> searchPosts(@RequestParam String keyword) {
        return ResponseEntity.ok(blogPostService.searchPostsByTitle(keyword));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        blogPostService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody BlogPost updatedPost) {
        Optional<BlogPost> existingPost = blogPostService.getPostById(id);
        if (existingPost.isPresent()) {
            BlogPost post = existingPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            return ResponseEntity.ok(blogPostService.createPost(post)); // Reuse createPost for saving
        }
        return ResponseEntity.notFound().build();
    }
}
