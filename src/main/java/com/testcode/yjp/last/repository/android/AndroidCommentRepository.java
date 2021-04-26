package com.testcode.yjp.last.repository.android;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AndroidCommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.board = :board order by c.id DESC ")
    ArrayList<Comment> findByBoard(Board board);


}
