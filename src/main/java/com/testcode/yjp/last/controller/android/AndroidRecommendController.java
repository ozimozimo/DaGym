package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.Recommend;
import com.testcode.yjp.last.domain.dto.android.AndBoardFindIdDto;
import com.testcode.yjp.last.domain.dto.android.AndLikeDto;
import com.testcode.yjp.last.repository.android.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/android/board/like")
public class AndroidRecommendController {
    private final AndroidMemberRepository androidMemberRepository;
    private final AndroidBoardRepository androidBoardRepository;
    private final AndroidRecommendRepository androidRecommendRepository;
    private final AndroidLikeRepository androidLikeRepository;

    @PostMapping("/add")
    public AndLikeDto addLike(@RequestBody AndLikeDto andLikeDto) {
        Long member_id = andLikeDto.getMember_id();
        Long board_id = andLikeDto.getBoard_id();
        int recomment_cnt = andLikeDto.getRecomment_cnt();

        Member member = androidMemberRepository.findById(member_id).orElse(null);
        Board board = androidBoardRepository.findById(board_id).orElse(null);
        Recommend recommend = new Recommend(member, board);

        Recommend ifRecommend = androidRecommendRepository.findByMemberAndBoard(member, board).orElse(null);
        if (ifRecommend == null) {
            androidRecommendRepository.save(recommend);
            andLikeDto.setBool("true");
            andLikeDto.setRecomment_cnt(recomment_cnt + 1);
        } else {
            androidRecommendRepository.delete(ifRecommend);
            andLikeDto.setBool("false");
            andLikeDto.setRecomment_cnt(recomment_cnt - 1);
            // 삭제되게 해야됨.
        }

        return andLikeDto;
    }

    @PostMapping("/select")
    public ArrayList<AndBoardFindIdDto> selectLike(@RequestBody Long member_id) {
        ArrayList<AndBoardFindIdDto> andBoardFindIdDtos = new ArrayList<>();

        Member member = androidMemberRepository.findById(member_id).get();
        ArrayList<Board> likeBoard = androidRecommendRepository.findLikeBoard(member);

        for (Board board : likeBoard) {
            int size = androidRecommendRepository.findByBoard(board).size();
            AndBoardFindIdDto andBoardFindIdDto = new AndBoardFindIdDto(
                    board.getId(),
                    board.getTitle(),
                    board.getUser_id(),
                    board.getContent(),
                    board.getMember(),
                    board.getRegDate().toString(),
                    board.getModDate().toString()
            );
            andBoardFindIdDto.setRecommend_cnt(size);

            log.info(andBoardFindIdDto.getId().toString());
            andBoardFindIdDtos.add(andBoardFindIdDto);
        }

        return andBoardFindIdDtos;
    }
}
