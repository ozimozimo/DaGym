package com.testcode.yjp.last.repository;


import com.testcode.yjp.last.domain.Comment;
import com.testcode.yjp.last.domain.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReCommentsRepository extends JpaRepository<ReComment, Long> {

    @Query("select r from ReComment r order by r.id DESC ")
    List<ReComment> findAllDesc();

    @Query("select r from Recommend r where r.id = :id")
    List<ReComment> findAllCount(List<Comment> id);


    @Modifying
    @Query("delete from ReComment r where r.re_parentNum=:id")
    void deleteByreId(Long id);
}
