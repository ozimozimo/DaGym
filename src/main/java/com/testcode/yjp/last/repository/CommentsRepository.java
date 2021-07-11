package com.testcode.yjp.last.repository;

import com.querydsl.core.BooleanBuilder;
import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Comment;
import com.testcode.yjp.last.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;


@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> , QuerydslPredicateExecutor<Comment>{




    @Query("select c from Comment c order by c.id DESC ")
    List<Comment> findAllDesc();


    @Query("select c from Comment c where c.parentNum = :hb_num")
    List<Comment> findAllCount(Long hb_num);


    @Modifying
    @Query("update Comment c set c.like_check = c.like_check+1 where c.id=:cm_id")
    void like(Long cm_id);

    @Modifying
    @Query("update Comment c set c.dislike_check = c.dislike_check+1 where c.id=:cm_id")
    void dislike(Long cm_id);

    @Modifying
    @Query("update Comment c set c.like_check = c.like_check+1, c.dislike_check=c.dislike_check-1  where c.id=:cm_id")
    void dislikeChange(Long cm_id);

    @Modifying
    @Query("update Comment c set c.like_check = c.like_check-1, c.dislike_check=c.dislike_check+1  where c.id=:cm_id")
    void likeChange(Long cm_id);

    @Query("select c from Comment c where c.parentNum = :parentNum")
    List<Comment> findByparentNum(Long parentNum);

    @Query("select c from Comment c where c.parentNum=:id order by c.like_check desc")
    List<Comment> findLikeAll(Long id);

    @Query("select c from Comment c where c.parentNum=:id order by c.dislike_check desc")
    List<Comment> findDisLikeAll(Long id);

    @Query("select c from Comment c where c.parentNum=:id order by c.regDate desc")
    List<Comment> findLatestAll(Long id);

    @Query("select c from Comment c where c.parentNum=:id order by c.regDate asc")
    List<Comment> findPastAll(Long id);

    @Query("select c from Comment c where c.parentNum=:id order by c.reComments_count desc")
    List<Comment> findCountAll(Long id);

    @Modifying
    @Query("delete from Comment c where c.parentNum=:id")
    void deleteByCmId(Long id);

    @Transactional
    @Modifying
    @Query("update Comment c set c.reComments_count = c.reComments_count + 1 where c.id = :re_parentCoNum")
    void update(Long re_parentCoNum);

    @Transactional
    @Modifying
    @Query("update Comment c set c.reComments_count = c.reComments_count - 1 where c.id= :re_parentCoNum")
    void reCount(Long re_parentCoNum);


//    @Modifying
//    @Query("update Comment c set c.like_check = c.like_check-1 where c.id=:comId")
//    void unlike(Long comId);

//
//    @Modifying
//    @Query("update Comment c set c.dislike_check = c.dislike_check-1 where c.id=:comId")
//    void undislike(Long comId);


//    @Query("select c from Comment c where c.board = :hb_num")
//    Comment findByParentNum(Long hb_num);
}
