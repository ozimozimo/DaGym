package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.Recommend;
import com.testcode.yjp.last.domain.dto.BoardListResponseDto;
import com.testcode.yjp.last.domain.dto.android.*;
import com.testcode.yjp.last.repository.android.AndroidBoardRepository;
import com.testcode.yjp.last.repository.android.AndroidMemberRepository;
import com.testcode.yjp.last.repository.android.AndroidRecommendRepository;
import com.testcode.yjp.last.service.android.AndBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/android/board")
public class AndroidBoardController {
    private final AndroidBoardRepository androidBoardRepository;
    private final AndBoardService andBoardService;
    private final AndroidMemberRepository androidMemberRepository;
    private final AndroidRecommendRepository androidRecommendRepository;

    // 게시판 조회
    @PostMapping("/select")
    public ArrayList<BoardListResponseDto> select() {
        log.info("BoardController select 1st Line");

        ArrayList<BoardListResponseDto> allDesc = (ArrayList<BoardListResponseDto>) andBoardService.findAllDesc();

        log.info("어우 렉걸려"+allDesc.toString());
        return allDesc;
    }

    //상세조회
    @PostMapping("/idSelect")
    public AndBoardFindIdDto idSelect(@RequestBody AndLikeDto andLikeDto) {
        Long board_id = andLikeDto.getBoard_id();
        Long member_id = andLikeDto.getMember_id();
        log.info("BoardController idSelect 1st Line");
        log.info("b_id = " + board_id + ", m_id = " + member_id);

        Board board = androidBoardRepository.findById(board_id).orElse(null);
        Member member = androidMemberRepository.findById(member_id).orElse(null);

        AndBoardFindIdDto andBoardFindIdDto = new AndBoardFindIdDto(
                board.getId(),
                board.getTitle(),
                board.getUser_id(),
                board.getContent(),
                board.getMember(),
                board.getRegDate().toString(),
                board.getModDate().toString()
        );

        Recommend ifRecommend = androidRecommendRepository.findByMemberAndBoard(member, board).orElse(null);

        int size = androidRecommendRepository.findByBoard(board).size();
        log.info("size = " + size);
        andBoardFindIdDto.setRecommend_cnt(size);
        if (ifRecommend == null) {
            andBoardFindIdDto.setBool("false");
        } else {
            andBoardFindIdDto.setBool("true");
        }

        return andBoardFindIdDto;
    }

    // 게시판 등록
    @PostMapping("/insert/{member_id}")
    public Board insert(@PathVariable("member_id") Long member_id, @RequestBody AndBoardSaveDto andBoardSaveDto) {

        log.info("BoardController insert 1st Line");

        Member member = androidMemberRepository.findById(member_id).get();
        andBoardSaveDto.setUser_id(member.getUser_id());
        andBoardSaveDto.setMember(member);
        return androidBoardRepository.save(andBoardSaveDto.toEntity());
    }

    // 게시판 수정
    @PutMapping("/update/{board_id}")
    public Board update(@PathVariable("board_id") Long board_id, @RequestBody AndBoardUpdateDto andBoardUpdateDto) {
        log.info("BoardController update 1st Line");

        Board board = androidBoardRepository.findById(board_id).get();
        board.setContent(andBoardUpdateDto.getContent());
        board.setTitle(andBoardUpdateDto.getTitle());

        return androidBoardRepository.save(board);
    }

    // 게시판 삭제
    @DeleteMapping("/delete/{board_id}")
    public Long delete(@PathVariable("board_id") Long board_id) {
        log.info("BoardController delete 1st Line");

        andBoardService.delete(board_id);
        return board_id;
    }

    // 게시판 검색
    @PostMapping("/search")
    public List<BoardListResponseDto> search(@RequestBody AndBoardSearchDto andBoardSearchDto) {
        log.info("BoardController search 1st Line");

        String head = andBoardSearchDto.getHead();
        String search = andBoardSearchDto.getSearch();
        if (head.equals("t")) {
            return androidBoardRepository.titleSearch(search);
        } else if (head.equals("c")) {
            return androidBoardRepository.contentSearch(search);
        } else if (head.equals("u")) {
            return androidBoardRepository.userSearch(search);
        }
        return andBoardService.findAllDesc();
    }
}
