package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Comment;
import com.testcode.yjp.last.domain.ReComment;
import com.testcode.yjp.last.repository.BoardRepository;
import com.testcode.yjp.last.repository.CommentsRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.ReCommentsRepository;
import com.testcode.yjp.last.service.CommentsService;
import com.testcode.yjp.last.service.ReCommentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recomments")
@Slf4j
public class ReCommentsApiController {
    private final ReCommentsRepository reCommentsRepository;
    private final BoardRepository boardRepository;
    private final CommentsRepository commentsRepository;
    private final ReCommentsService reCommentsService;

    @PostMapping("/save/{re_parentCoNum}")
    public ReComment RecommentsSave(@PathVariable Long re_parentCoNum, @RequestBody ReComment reComment, Model model) {
        log.info("Recomments save Controller");
        System.out.println("로그인한 아이디"+reComment.getRe_user_id());
        System.out.println("댓글부모"+reComment.getRe_parentCoNum());
        Optional<Comment> result = commentsRepository.findById(re_parentCoNum);
        reComment.setComment(result.get());
        reCommentsRepository.save(reComment);

        commentsRepository.update(re_parentCoNum);

        model.addAttribute("reComment", commentsRepository.findById(re_parentCoNum));

        return reComment;
    }


    //수정기능
//    @PostMapping("/update/{id}")
//    public Long commentUpdate(@PathVariable Long id,@RequestBody CommentsUpdateRequestDto commentsUpdateRequestDto){
//        log.info("post comments update controller");
//        System.out.println(id);
//        return commentsService.update(id, commentsUpdateRequestDto);
//    }

//    삭제기능 url: '/recomments/delete/'+id ,
    @PostMapping("/delete/{id}")
    public Long delete(@PathVariable Long id){
        reCommentsService.delete(id);
        return id;
    }

}
