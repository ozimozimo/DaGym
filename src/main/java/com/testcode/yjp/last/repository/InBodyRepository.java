package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.InBody;
import com.testcode.yjp.last.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface InBodyRepository extends JpaRepository<InBody, Long> {

    @Query("select i from InBody i where i.inBody_user_id=:inBodyId order by i.inBody_date")
    List<InBody> findAllDesc(String inBodyId);


    @Query("select i from InBody i where i.inBody_user_id=:inBodyId order by i.inBody_date")
    List<InBody> findByInBody(String inBodyId);
}
