package com.moviecatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moviecatalog.model.Comment;
import com.moviecatalog.model.User;

@Repository("commentRepository")
public interface CommentRepository  extends JpaRepository<Comment, Integer> {
	Comment findByAuthor(User user);
}
