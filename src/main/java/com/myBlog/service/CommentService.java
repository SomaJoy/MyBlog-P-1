package com.myBlog.service;

import com.myBlog.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, int postId);

    void deleteComment(long id);

    CommentDto updatecomment(long id, CommentDto commentDto);
}
