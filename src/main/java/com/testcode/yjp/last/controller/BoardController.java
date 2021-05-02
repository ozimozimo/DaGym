package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Comment;
import com.testcode.yjp.last.domain.dto.*;
import com.testcode.yjp.last.repository.BoardRepository;
import com.testcode.yjp.last.repository.CommentsRepository;
import com.testcode.yjp.last.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentsService commentsService;
    private final ReCommentsService reCommentsService;
    private final CommentsRepository commentsRepository;
    private final BoardRepository boardRepository;


    // 전체조회
    @GetMapping("")
    public String BoardView(PageRequestDto pageRequestDto, Model model) {
        model.addAttribute("boards", boardService.findAllDesc());
        model.addAttribute("result", boardService.getList(pageRequestDto));
        model.addAttribute("PageRequestDto", pageRequestDto);
        return "board/boardSelect";
    }

    // 저장페이지
    @GetMapping("/trainerBoard/save")
    public String trainerBoardSave(Long member_id,Model model) {
        log.info("save get Controller");
        System.out.println(member_id);
        return "board/boardView";
    }

    // 수정페이지
    @GetMapping("/trainerBoard/update")
    public String trainerBoardUpdate( Long hb_num, Model model, @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        BoardResponseDto dto = boardService.findById(hb_num);
        model.addAttribute("boards", dto);
        return "board/boardUpdate";
    }

//    @GetMapping("/trainerBoard/detail")
//    public String trainerCommentDetail(Long cm_id, Model model, @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
//        model.addAttribute("comment", commentsService.findById(cm_id));
//        return "board/boardDetail";
//    }

    // 게시판 디테일 페이지
    @GetMapping("/trainerBoard/detail")
    public String trainerBoardDetail(Long hb_num ,Model model,@ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {


        model.addAttribute("boards", boardService.findById(hb_num));
        model.addAttribute("re_comments", reCommentsService.findAllDesc());
//        model.addAttribute("re_count", reCommentsService.findCount(hb_num).size());

        model.addAttribute("comments", commentsService.findAllDesc());
//        model.addAttribute("result", boardService.getList(pageRequestDto));
//        model.addAttribute("CoResult", commentsService.getList(pageCommentRequestDto));
//        model.addAttribute("comment", commentsService.findById(cm_id));
        model.addAttribute("commentLikeAll",commentsService.findLikeAll(hb_num));
        model.addAttribute("commentDisLikeAll",commentsService.findDisLikeAll(hb_num));
        model.addAttribute("commentLikeLatestAll",commentsService.findLatestAllClass(hb_num));
        model.addAttribute("commentLikePastAll",commentsService.findPastAllClass(hb_num));
        model.addAttribute("commentCountAll", commentsService.findCountAllClass(hb_num));

        boardService.updateView(hb_num);

        List<CommentsListResponseDto> count = commentsService.findAllCount(hb_num);
        model.addAttribute("count",count.size());
        System.out.println("전체크기는"+count.size());

        // board_id  값
        Optional<Board> result = boardRepository.findById(hb_num);
        commentsRepository.findByparentNum(result.get().getId());

        System.out.println("commentId==="+commentsRepository.findByparentNum(result.get().getId()));


        // img 보내기


        return "board/boardDetail";
    }



}
