package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Notice;
import com.testcode.yjp.last.domain.dto.*;
import com.testcode.yjp.last.repository.BoardRepository;
import com.testcode.yjp.last.repository.NoticeRepository;
import com.testcode.yjp.last.service.AdminService;
import com.testcode.yjp.last.service.BoardService;
import com.testcode.yjp.last.service.CommentsService;
import com.testcode.yjp.last.service.ReCommentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final CommentsService commentsService;
    private final ReCommentsService reCommentsService;
    private final NoticeRepository noticeRepository;

    @GetMapping("/adminMain")
    public String adminPage() {
        return "admin/adminMain";
    }

    @GetMapping("/userManagement")
    public String userManagement(Model model) {
        List<MemberList> memberLists = adminService.selectUser();
        log.info("memberLists = " + memberLists);
        model.addAttribute("members", memberLists);
        model.addAttribute("count", memberLists.size());
        model.addAttribute("tr_if", "user");

        return "admin/member/userManagement";
    }

    @GetMapping("/trainerManagement")
    public String trainerManagement(Model model) {
        List<MemberList> memberLists = adminService.selectTrainer();
        model.addAttribute("members", memberLists);
        model.addAttribute("count", memberLists.size());
        model.addAttribute("tr_if", "trainer");
        return "admin/member/userManagement";
    }

    @GetMapping("/boardManagement")
    public String boardManagement(PageRequestDto pageRequestDto, Model model) {
        List<Board> allDesc = boardRepository.findAllDesc();
        PageResultDto<BoardDto, Board> boardList = adminService.getBoardList(pageRequestDto);
        log.info("게시판 개수 = " + boardList.getDtoList().size());
        model.addAttribute("result", boardList);
        model.addAttribute("PageRequestDto", pageRequestDto);
        return "admin/board/boardManagement";
    }

    @GetMapping("/boardManagement/detail")
    public String boardManagementDetail(Model model, Long hb_num,@ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        log.info("hb_num = " + hb_num);
        Board board = boardRepository.findById(hb_num).get();
        model.addAttribute("boards", board);
        model.addAttribute("boards", boardService.findById(hb_num));
        model.addAttribute("comments", commentsService.findAllDesc());
        model.addAttribute("commentLikeAll",commentsService.findLikeAll(hb_num));
        model.addAttribute("commentDisLikeAll",commentsService.findDisLikeAll(hb_num));
        model.addAttribute("commentLikeLatestAll",commentsService.findLatestAllClass(hb_num));
        model.addAttribute("commentLikePastAll",commentsService.findPastAllClass(hb_num));
        model.addAttribute("commentCountAll", commentsService.findCountAllClass(hb_num));
        model.addAttribute("re_comments", reCommentsService.findAllDesc());

        boardService.updateView(hb_num);

        model.addAttribute("count", commentsService.findAllCount(hb_num).size());
        return "admin/board/boardManagementDetail";
    }
    @GetMapping("/noticeManagement")
    public String noticeManagement(Model model, PageRequestDto pageRequestDto) {
        PageResultDto<NoticeDto, Notice> list = adminService.getList(pageRequestDto);
        model.addAttribute("notice", list);
        model.addAttribute("PageRequestDto", pageRequestDto);

        return "admin/notice/noticeManagement";
    }

    @GetMapping("/noticeDetail")
    public String noticeDetail(Model model, Long id, @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        log.info("id = " + id);
        Notice notice = noticeRepository.findById(id).get();
        model.addAttribute("notices", notice);
        adminService.updateView(id);
        return "admin/notice/noticeDetail";
    }

    @GetMapping("/noticeInsertView")
    public String noticeInsert(Model model) {
        return "admin/notice/noticeInsert";
    }

    @GetMapping("/noticeUpdateView")
    public String noticeUpdate(Model model, Long id, @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        model.addAttribute("notices", noticeRepository.findById(id).get());
        return "admin/notice/noticeUpdate";
    }
}



