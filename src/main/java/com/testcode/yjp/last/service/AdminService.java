package com.testcode.yjp.last.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.Notice;
import com.testcode.yjp.last.domain.QBoard;
import com.testcode.yjp.last.domain.dto.*;
import com.testcode.yjp.last.repository.BoardRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final BoardRepository boardRepository;

    public List<MemberList> selectUser() {
        List<Member> members = memberRepository.selectUser();
        List<MemberList> memberLists = new ArrayList<>();
        for(Member member : members) {
            MemberList memberList = new MemberList();
            memberList.setId(member.getId());
            memberList.setUser_name(member.getUser_name());
            memberList.setUser_id(member.getUser_id());
            memberList.setUser_pn(member.getUser_pn());
            memberList.setUser_rrn(member.getUser_rrn());
            memberList.setAddress_normal(member.getAddress_normal());
            memberList.setAddress_detail(member.getAddress_detail());
            memberList.setRegDate(member.getRegDate());

            memberLists.add(memberList);
        }
        return memberLists;
    }

    public List<MemberList> selectTrainer() {
        List<Member> members = memberRepository.selectTrainer();
        List<MemberList> memberLists = new ArrayList<>();
        for(Member member : members) {
            MemberList memberList = new MemberList();
            memberList.setId(member.getId());
            memberList.setUser_name(member.getUser_name());
            memberList.setUser_id(member.getUser_id());
            memberList.setUser_pn(member.getUser_pn());
            memberList.setUser_rrn(member.getUser_rrn());
            memberList.setAddress_normal(member.getAddress_normal());
            memberList.setAddress_detail(member.getAddress_detail());
            memberList.setRegDate(member.getRegDate());
            memberLists.add(memberList);
        }
        return memberLists;
    }

    public PageResultDto<NoticeDto, Notice> getList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());
        Page<Notice> result = noticeRepository.findAll(pageable);
        Function<Notice, NoticeDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(result, fn);
    }

    private NoticeDto entityToDto(Notice entity) {
        NoticeDto dto = NoticeDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .user_id(entity.getUser_id())
                .active(entity.getActive())
                .hit(entity.getHit())
                .regDate(entity.getRegDate())
                .modifiedDate(entity.getModDate())
                .build();
        return dto;
    }

    public PageResultDto<BoardDto, Board> getBoardList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDto);
        Page<Board> result = boardRepository.findAll(booleanBuilder, pageable);
        Function<Board, BoardDto> function = (entity -> boardToDto(entity));
        return new PageResultDto<>(result, function);
    }

    private BoardDto boardToDto(Board entity) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(entity.getId());
        boardDto.setTitle(entity.getTitle());
        boardDto.setContent(entity.getContent());
        boardDto.setUser_id(entity.getUser_id());
        boardDto.setHit(entity.getHit());
        boardDto.setRecommend(entity.getRecommends().size());
        boardDto.setRegDate(entity.getRegDate());
        boardDto.setModifiedDate(entity.getModDate());
        return boardDto;
    }

    private BooleanBuilder getSearch(PageRequestDto requestDto) {
        String type = requestDto.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBoard qBoard = QBoard.board;
        String keyword = requestDto.getKeyword();
        BooleanExpression booleanExpression = qBoard.id.gt(0L);
        booleanBuilder.and(booleanExpression);

        if(type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")) {
            conditionBuilder.or(qBoard.title.contains(keyword));
        }
        if(type.contains("c")) {
            conditionBuilder.or(qBoard.content.contains(keyword));
        }
        if(type.contains("u")) {
            conditionBuilder.or(qBoard.user_id.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

    @Transactional
    public int updateView(Long id) {
        log.info("조회수 증가 서비스");
        return noticeRepository.updateView(id);
    }



}