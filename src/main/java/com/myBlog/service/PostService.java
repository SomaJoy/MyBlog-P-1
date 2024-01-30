package com.myBlog.service;

import com.myBlog.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostByid(int id);

    void deletePost(int id);
}
