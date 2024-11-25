package com.example.blog.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.entity.Post;
import com.example.blog.repository.PostRepository;

@Service
public class PostService {

	@Autowired
    private PostRepository blogRepository;

    public Post addBlog(Post blog) {
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());
        return blogRepository.save(blog);
    }

    public Post updateBlog(Long id, Post updatedBlog) {
        Optional<Post> existingBlog = blogRepository.findById(id);

        if (existingBlog.isPresent()) {
            Post blog = existingBlog.get();
            blog.setTitle(updatedBlog.getTitle());
            blog.setContent(updatedBlog.getContent());
            blog.setUpdatedAt(LocalDateTime.now());
            return blogRepository.save(blog);
        } else {
            throw new RuntimeException("Blog not found with id: " + id);
        }
    }

    public void deleteBlog(Long id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
        } else {
            throw new RuntimeException("Blog not found with id: " + id);
        }
    }
}

