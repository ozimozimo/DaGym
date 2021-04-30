package com.testcode.yjp.last.repository.android;

import com.testcode.yjp.last.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface AndroidBoardRepository extends JpaRepository<Board, Long> {
    //OR m.content like %:title%
    @Query("select m from Board m where m.title LIKE %:title% OR m.content like %:title% ORDER BY m.id desc")
    ArrayList<Board> titleSearch(String title);

    @Query("select m from Board m where m.content like :content ORDER BY m.id desc")
    ArrayList<Board> contentSearch(String content);
//
//    @Query("select m from Board m where m.user_id like %:user_id% ORDER BY m.id desc")
//    List<BoardListResponseDto> userSearch(String user_id);
}
