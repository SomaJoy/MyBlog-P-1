package com.myBlog.controller;

import com.myBlog.entity.Post;
import com.myBlog.payload.PostDto;
import com.myBlog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PostDto> getAllPosts(
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", required = false, defaultValue = "ASC") String sortDir
    ){
        List<PostDto> dtos = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return dtos;
    }

    //http://localhost:8080/api/posts/id?id=0
    @GetMapping("/id")
    public ResponseEntity<PostDto> getPostById(@RequestParam int id){
        PostDto dto = postService.getPostByid(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
