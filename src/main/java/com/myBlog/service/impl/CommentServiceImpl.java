package com.myBlog.service.impl;

import com.myBlog.entity.Comment;
import com.myBlog.entity.Post;
import com.myBlog.exception.ResourceNotFoundException;
import com.myBlog.payload.CommentDto;
import com.myBlog.repository.CommentRepository;
import com.myBlog.repository.PostRepository;
import com.myBlog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, int postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found with id - "+postId)
        );

        Comment comment = new Comment();
        comment.setEmail(commentDto.getEmail());
        comment.setText(commentDto.getText());
        comment.setPost(post);
        Comment saveComment = commentRepository.save(comment);

        CommentDto dto = new CommentDto();
        dto.setId(saveComment.getId());
        dto.setText(saveComment.getText());
        dto.setEmail(saveComment.getEmail());
        return dto;
    }

    @Override
    public void deleteComment(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Comment does not exist with id - "+id)
        );
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDto updatecomment(long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Comment does not exist with id - "+id)
        );
        comment.setText(commentDto.getText());
        comment.setEmail(commentDto.getEmail());
        Comment updatedComment = commentRepository.save(comment);
        CommentDto dto = modelMapper.map(updatedComment, CommentDto.class);
        return dto;
    }
}
