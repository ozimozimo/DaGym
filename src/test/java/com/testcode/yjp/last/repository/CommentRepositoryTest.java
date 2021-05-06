//package com.testcode.yjp.last.repository;
//
//import com.testcode.yjp.last.domain.Board;
//import com.testcode.yjp.last.domain.Comment;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.stream.IntStream;
//
//@SpringBootTest
//public class CommentRepositoryTest {
//
//    @Autowired
//    private CommentsRepository commentsRepository;
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//
//    @Test
//    public void Dummies() {
//        IntStream.rangeClosed(1, 300).forEach(i -> {
//
////            Board board = Board.builder().id((long) i).build();
//s
//            Comment comment = Comment.builder()
//                    .user_id("작성자" + i)
//                    .comments("내용" + i)
//                    .build();
//            System.out.println(commentsRepository.save(comment));
//        });
//    }
//}