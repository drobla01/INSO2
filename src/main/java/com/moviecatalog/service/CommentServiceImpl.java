package com.moviecatalog.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviecatalog.model.Comment;
import com.moviecatalog.model.User;
import com.moviecatalog.repository.CommentRepository;
import com.moviecatalog.repository.UserRepository;

@Service("commentService")
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}

	@Override
	public List<Comment> findAllComments() {
		return commentRepository.findAll();
	}
}
