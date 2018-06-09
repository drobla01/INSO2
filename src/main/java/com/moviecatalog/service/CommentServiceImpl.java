package com.moviecatalog.service;

import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviecatalog.model.Comment;
import com.moviecatalog.model.User;
import com.moviecatalog.repository.CommentRepository;

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

	@Override
	public int countComments(User author) {
		List<Comment> allComments = commentRepository.findAll();
		int nComments = 0;
		for (Iterator<Comment> iterator = allComments.iterator(); iterator.hasNext();) {
			Comment c = iterator.next();
			if (c.getAuthor().getId().equals(author.getId()))
				nComments++;
		}
		return nComments;
	}

	@Override
	public Comment findCommentById(Integer id) {
		return commentRepository.findById(id).get();
	}

	@Override
	public void deleteComment(Comment comment) {
		commentRepository.delete(comment);
	}


}
