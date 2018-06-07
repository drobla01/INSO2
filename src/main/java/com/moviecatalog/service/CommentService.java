package com.moviecatalog.service;

import java.util.List;

import com.moviecatalog.model.Comment;

public interface CommentService {
	public void saveComment(Comment comment);
	public List<Comment> findAllComments();
}
