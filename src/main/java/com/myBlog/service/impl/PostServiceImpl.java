package com.myBlog.service.impl;

import com.myBlog.entity.Post;
import com.myBlog.exception.ResourceNotFoundException;
import com.myBlog.payload.PostDto;
import com.myBlog.repository.PostRepository;
import com.myBlog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToPostEntity(postDto);
        Post savePost = postRepository.save(post);
        PostDto dto = mapToDTo(savePost);
        return dto;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePost = postRepository.findAll(pageable);
        List<Post> posts = pagePost.getContent();
        List<PostDto> dtos = posts.stream().map(post -> mapToDTo(post)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public PostDto getPostByid(int id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id :"+id)
        );
        PostDto dto = new PostDto();
        dto = mapToDTo(post);
        return dto;
    }

    @Override
    public void deletePost(int id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found with id - "+id)
        );
        postRepository.deleteById(id);
    }

    public PostDto mapToDTo(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }

    public Post mapToPostEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }
}
