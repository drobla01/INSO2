package com.moviecatalog.service;

import java.util.List;

import com.moviecatalog.model.Comment;
import com.moviecatalog.model.User;

public interface CommentService {
	public void saveComment(Comment comment);
	public List<Comment> findAllComments();
	public int countComments(User author);
	public Comment findCommentById(Integer id);
	public void deleteComment(Comment comment);
}
