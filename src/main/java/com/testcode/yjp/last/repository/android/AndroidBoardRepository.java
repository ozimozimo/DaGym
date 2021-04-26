package com.testcode.yjp.last.repository.android;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.dto.BoardListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AndroidBoardRepository extends JpaRepository<Board, Long> {
    //    @Query("select m from Member m where user_role like 'trainer' and user_name = :user_name ")
    @Query("select m from Board m where m.title like %:title% ORDER BY m.id desc")
    List<BoardListResponseDto> titleSearch(String title);

    @Query("select m from Board m where m.content like %:content% ORDER BY m.id desc")
    List<BoardListResponseDto> contentSearch(String content);

    @Query("select m from Board m where m.user_id like %:user_id% ORDER BY m.id desc")
    List<BoardListResponseDto> userSearch(String user_id);
}
