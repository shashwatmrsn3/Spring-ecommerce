package com.home.ecommerce.Repositroy;

import com.home.ecommerce.Domain.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Integer> {

    Comment findById(int id);
}
