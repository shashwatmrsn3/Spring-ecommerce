package com.home.ecommerce.Service;

import com.home.ecommerce.Domain.Comment;
import com.home.ecommerce.Repositroy.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment){
        return commentRepository.save(comment);
    }

    public Comment findById(int id){
        return commentRepository.findById(id);
    }
}
