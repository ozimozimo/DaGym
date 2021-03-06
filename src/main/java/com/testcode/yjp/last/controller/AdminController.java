package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.*;
import com.testcode.yjp.last.domain.dto.*;
import com.testcode.yjp.last.repository.*;
import com.testcode.yjp.last.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
    private final MemberRepository memberRepository;
    private final OooRepository oooRepository;
    private final PTUserRepository ptUserRepository;
    private final PTUserService ptUserService;

    //메인
    @GetMapping(value = {"/adminMain", "/", ""})
    public String adminPage(Model model) {
        int userSize = memberRepository.selectUser().size();
        int trainerSize = memberRepository.selectTrainer().size();
        int boardSize = boardRepository.findAll().size();
        List<OneOnOne> byAnswerIsNull = oooRepository.findByAnswerIsNull();
        List<OneOnOne> oneOnOnes = new ArrayList<>();
        List<Member> userList = memberRepository.selectUser();
        List<Member> trainerList = memberRepository.selectTrainer();
        List<Member> users = new ArrayList<>();
        List<Member> trainers = new ArrayList<>();
//        if (userList.size() >= 5 || trainerList.size() >= 5) {
        for (int i = 0; i < 5; i++) {
            try {
                users.add(userList.get(i));
            } catch (Exception e) {
            }
            try {
                trainers.add(trainerList.get(i));
            } catch (Exception e) {
            }
            try {
                oneOnOnes.add(byAnswerIsNull.get(i));
            } catch (Exception e) {
            }
        }
//        }
//        else {
//            users = userList;
//            trainers = trainerList;
//        }

        model.addAttribute("user_list", users);
        model.addAttribute("trainer_list", trainers);
        model.addAttribute("user", userSize);
        model.addAttribute("trainer", trainerSize);
        model.addAttribute("board", boardSize);
        model.addAttribute("ooos", oneOnOnes);
        return "admin/adminMain";
    }

    //회원관리
    @GetMapping("/userManagement")
    public String userManagement(Model model, @ModelAttribute("PageRequestDto") PageRequestDto requestDto) {
        PageResultDto<MemberList, Member> memberList = adminService.getMemberList(requestDto, "user");
        model.addAttribute("members", memberList);
        model.addAttribute("tr_if", "user");

        return "admin/member/userManagement";
    }

    //트레이너관리
    @GetMapping("/trainerManagement")
    public String trainerManagement(Model model, @ModelAttribute("PageRequestDto") PageRequestDto requestDto) {
        PageResultDto<MemberList, Member> memberList = adminService.getMemberList(requestDto, "trainer");
        model.addAttribute("members", memberList);
        model.addAttribute("tr_if", "trainer");

        return "admin/member/userManagement";
    }

    //게시판 관리
    @GetMapping("/boardManagement")
    public String boardManagement(PageRequestDto pageRequestDto, Model model) {
        List<Board> allDesc = boardRepository.findAllDesc();
        PageResultDto<BoardDto, Board> boardList = adminService.getBoardList(pageRequestDto);
        log.info("게시판 개수 = " + boardList.getDtoList().size());
        model.addAttribute("result", boardList);
        model.addAttribute("PageRequestDto", pageRequestDto);
        return "admin/board/boardManagement";
    }

    //게시판 상세
    @GetMapping("/boardManagement/detail")
    public String boardManagementDetail(Model model, Long hb_num, @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        log.info("hb_num = " + hb_num);
        Board board = boardRepository.findById(hb_num).get();
        model.addAttribute("boards", board);
        model.addAttribute("boards", boardService.findById(hb_num));
        model.addAttribute("comments", commentsService.findAllDesc());
        model.addAttribute("commentLikeAll", commentsService.findLikeAll(hb_num));
        model.addAttribute("commentDisLikeAll", commentsService.findDisLikeAll(hb_num));
        model.addAttribute("commentLikeLatestAll", commentsService.findLatestAllClass(hb_num));
        model.addAttribute("commentLikePastAll", commentsService.findPastAllClass(hb_num));
        model.addAttribute("commentCountAll", commentsService.findCountAllClass(hb_num));
        model.addAttribute("re_comments", reCommentsService.findAllDesc());

        boardService.updateView(hb_num);

        model.addAttribute("count", commentsService.findAllCount(hb_num).size());
        return "admin/board/boardManagementDetail";
    }

    // 공지사항 관리
    @GetMapping("/noticeManagement")
    public String noticeManagement(Model model, PageRequestDto pageRequestDto) {
        PageResultDto<NoticeDto, Notice> list = adminService.getBetweenList(pageRequestDto, 0, 1);
        model.addAttribute("notice", list);
        model.addAttribute("PageRequestDto", pageRequestDto);

        return "admin/notice/noticeManagement";
    }

    //공지사항 상세
    @GetMapping("/noticeDetail")
    public String noticeDetail(Model model, Long id, @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        log.info("id = " + id);
        Notice notice = noticeRepository.findById(id).get();
        model.addAttribute("notices", notice);
        adminService.updateView(id);

        return "admin/notice/noticeDetail";
    }

    //공지사항 등록 뷰
    @GetMapping("/noticeInsertView")
    public String noticeInsert(Model model) {

        return "admin/notice/noticeInsert";
    }

    //공지사항 수정 뷰
    @GetMapping("/noticeUpdateView")
    public String noticeUpdate(Model model, Long id, @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        model.addAttribute("notices", noticeRepository.findById(id).get());

        return "admin/notice/noticeUpdate";
    }

    //FAQ
    @GetMapping("/faqManagement")
    public String faqManagement(Model model, PageRequestDto pageRequestDto) {
        PageResultDto<NoticeDto, Notice> list = adminService.getList(pageRequestDto, 2);
        model.addAttribute("notice", list);
        model.addAttribute("PageRequestDto", pageRequestDto);

        return "admin/FAQ/faqManagement";
    }

    @GetMapping("/faqInsertView")
    public String faqInsert(Model model) {
        return "admin/FAQ/faqInsert";
    }

    @GetMapping("/faqDetail")
    public String faqDetail(Model model, Long id, @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        Notice notice = noticeRepository.findById(id).get();
        model.addAttribute("faq", notice);
        return "admin/FAQ/faqDetail";
    }

    @GetMapping("/faqUpdateView")
    public String faqUpdateView(Model model, Long id, @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        Notice notice = noticeRepository.findById(id).get();
        model.addAttribute("notices", notice);
        return "admin/FAQ/faqUpdate";
    }


    @GetMapping("/1on1Management")
    public String oneOnOneManagement(Model model, PageRequestDto pageRequestDto, String category) {
        PageResultDto<OooDto, OneOnOne> oooList = adminService.getOooList(pageRequestDto, category);
        model.addAttribute("ooo", oooList);
        model.addAttribute("PageRequestDto", pageRequestDto);
        return "admin/oneOnOne/oneOnOneManagement";
    }

    @GetMapping("/1on1Detail")
    public String oneOnOneDetail(Model model, Long id, @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        log.info("oneOnOneDetail in");
        OneOnOne oneOnOne = oooRepository.findById(id).get();
        model.addAttribute("ooo", oneOnOne);
        return "admin/oneOnOne/oneOnOneDetail";
    }

    @GetMapping("/ptManagement")
    public String ptManagement(Model model, PageRequestDto pageRequestDto) {
        PageResultDto<PTMemberInfoDto, PTUser> list = ptUserService.getPTList(pageRequestDto);
        model.addAttribute("PTList",list);
        model.addAttribute("PageRequestDto", pageRequestDto);

        return "admin/PT/ptManagement";
    }

    @GetMapping("/payManagement")
    public String payManagement(Model model, PageRequestDto pageRequestDto) {
        log.info("payManagement");
        PageResultDto<BuyerPTDto, BuyerPt> payList = adminService.getPayList(pageRequestDto);
        model.addAttribute("payLists", payList);
        model.addAttribute("PageRequestDto", pageRequestDto);
        return "admin/payment/payManagement";
    }
}