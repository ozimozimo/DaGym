package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class CommentRepositoryTest {

    @Autowired
    private CommentsRepository commentsRepository;

    @Test
    public void Dummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {

            Comment comment = Comment.builder()
                    .user_id("작성자" + i)
                    .comments("내용" + i)
                    .parentNum(Long.valueOf(i))
                    .build();
            System.out.println(commentsRepository.save(comment));
        });
    }
}