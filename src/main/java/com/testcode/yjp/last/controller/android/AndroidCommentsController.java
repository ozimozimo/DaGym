package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Comment;
import com.testcode.yjp.last.domain.dto.android.AndCommentDto;
import com.testcode.yjp.last.repository.android.AndroidBoardRepository;
import com.testcode.yjp.last.repository.android.AndroidCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/android/board/comment")
public class AndroidCommentsController {

    private final AndroidBoardRepository androidBoardRepository;
    private final AndroidCommentRepository androidCommentRepository;

    @PostMapping("select/{board_id}")
    public ArrayList<AndCommentDto> selectAll(@PathVariable("board_id") Long board_id) {
        log.info("++selectComment in++");
        Board board = androidBoardRepository.findById(board_id).orElse(null);
        ArrayList<Comment> byBoard = androidCommentRepository.findByBoard(board);
        ArrayList<AndCommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : byBoard) {
            AndCommentDto andCommentDto = new AndCommentDto(
                    comment.getId(),
                    comment.getUser_id(),
                    comment.getComments(),
                    comment.getModDate()
            );
            commentDtos.add(andCommentDto);
        }

        return commentDtos;
    }

    @PostMapping("insert/{user_id}/{board_id}")
    public Long insertComment(@PathVariable("user_id") String user_id, @PathVariable("board_id") Long board_id, @RequestBody String content) {
        Board board = androidBoardRepository.findById(board_id).orElse(null);
        content = content.replaceAll("\\\"", "");
        //String user_id, String comments, Long parentNum
        Comment comment = new Comment(user_id, content, board_id);
        comment.setBoard(board);

        return androidCommentRepository.save(comment).getId();

    }
}
