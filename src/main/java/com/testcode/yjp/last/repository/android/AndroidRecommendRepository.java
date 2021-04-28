package com.testcode.yjp.last.repository.android;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface AndroidRecommendRepository extends JpaRepository<Recommend, Long> {

    Optional<Recommend> findByMemberAndBoard(Member member, Board board);

    @Query("select m from Recommend m where m.board = :board")
    ArrayList<Recommend> findByBoard(Board board);

    @Query("select m.board from Recommend m where m.member = :member")
    ArrayList<Board> findLikeBoard(Member member);


}
