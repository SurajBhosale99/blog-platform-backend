package com.example.blog.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;

@Service
public class PostService {

	@Autowired
    private PostRepository blogRepository;
	
	@Autowired
	private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.blogRepository = postRepository;
        this.userRepository = userRepository;
    }


    public Post addBlog(String email,Post blog) {
    	
    	User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    	blog.setUser(user);
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

