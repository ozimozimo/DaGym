//package com.testcode.yjp.last.repository;
//
//import com.testcode.yjp.last.domain.Board;
//import com.testcode.yjp.last.domain.Comment;
//import com.testcode.yjp.last.domain.dto.CommentResponseDto;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.stream.IntStream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class BoardRepositoryTest {
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//
//
//    @Test
//    public void Dummies() {
//        IntStream.rangeClosed(1,100).forEach(i -> {
//
//            Board board = Board.builder()
//                    .title("제목"+i)
//                    .content("내용"+i)
//                    .user_id("작성자"+i)
//                    .build();
//            System.out.println(boardRepository.save(board));
//        });
//    }
//}