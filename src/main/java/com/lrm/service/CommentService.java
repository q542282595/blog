package com.lrm.service;

import com.lrm.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long id);
    Comment saveComment(Comment comment);
}
