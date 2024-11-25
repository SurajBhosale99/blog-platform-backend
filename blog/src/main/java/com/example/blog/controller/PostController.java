package com.example.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/blogs")
@Tag(name = "Blog API", description = "Operations related to blog posts")
public class PostController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
    private PostService blogService;
    @Operation(summary = "Add a new blog post", description = "Creates a new blog post in the system")

	@PostMapping
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<Post> addBlog(@RequestBody Post post , HttpServletRequest request) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : null;
    	System.out.print("Username :" +username);
    	
    	if (username == null) {
            return ResponseEntity.status(401).build();
        }
        Post savedBlog = blogService.addBlog(username, post);
        return ResponseEntity.ok(savedBlog);
    }

    @Operation(summary = "Update blog post", description = "update a blog Post in the system")

    @PutMapping("/{id}")
    public ResponseEntity<Post> updateBlog(@PathVariable Long id, @RequestBody Post blog) {
        Post updatedBlog = blogService.updateBlog(id, blog);
        return ResponseEntity.ok(updatedBlog);
    }

    @Operation(summary = "Delete blog post", description = "Delete a blog Post in the system")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok("Blog deleted successfully.");
    }
    
    @Operation(summary = "Add Comment on post", description = "Use can add comments on post and multiple times.")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody Comment comment) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : null;
        Comment savedComment = commentService.addComment(postId, comment);
        return ResponseEntity.ok(savedComment);
    }
    
    @Operation(summary = "Fetch comments by post ID", description = "Retrieve all comments associated with a specific blog post")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

}

