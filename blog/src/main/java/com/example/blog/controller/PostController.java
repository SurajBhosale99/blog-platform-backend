package com.example.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<Post> addBlog(@RequestBody Post post) {
        Post savedBlog = blogService.addBlog(post);
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
        Comment savedComment = commentService.addComment(postId, comment);
        return ResponseEntity.ok(savedComment);
    }

}

