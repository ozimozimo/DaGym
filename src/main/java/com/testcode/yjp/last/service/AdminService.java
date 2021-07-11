package com.testcode.yjp.last.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.testcode.yjp.last.domain.*;
import com.testcode.yjp.last.domain.dto.*;
import com.testcode.yjp.last.repository.*;
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
    private final OooRepository oooRepository;
    private final BuyerPTRepository buyerPTRepository;

    public PageResultDto<NoticeDto, Notice> getList(PageRequestDto requestDto, int active) {
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());
        Page<Notice> result = noticeRepository.findByActive(active, pageable);
        Function<Notice, NoticeDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(result, fn);
    }

    public PageResultDto<BuyerPTDto, BuyerPt> getPayList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());
        BooleanBuilder builder = getPaySearch(requestDto);
        Page<BuyerPt> result = buyerPTRepository.findAll(builder, pageable);
        Function<BuyerPt, BuyerPTDto> fn = (entity -> buyerPtToDto(entity));
        return new PageResultDto<>(result, fn);
    }

    public PageResultDto<NoticeDto, Notice> getBetweenList(PageRequestDto requestDto, int fromActive, int toActive) {
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());
        Page<Notice> result = noticeRepository.findByActiveBetween(fromActive, toActive, pageable);
        Function<Notice, NoticeDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(result, fn);
    }

    public PageResultDto<OooDto, OneOnOne> getOooList(PageRequestDto requestDto, String category) {
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());
        Page<OneOnOne> all = null;
        try {
            if (category.equals(null)) {
                log.info("hi");
            } else if (category.equals("전체보기")) {
                all = oooRepository.findAll(pageable);
            } else if (category.equals("미완료")) {
                all = oooRepository.findByAnswerIsNull(pageable);
            } else {
                all = oooRepository.findByCategory(category, pageable);
            }
        } catch (Exception e) {
            all = oooRepository.findAll(pageable);
        }
        Function<OneOnOne, OooDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(all, fn);
    }

    public PageResultDto<BoardDto, Board> getBoardList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDto);
        Page<Board> result = boardRepository.findAll(booleanBuilder, pageable);
        Function<Board, BoardDto> function = (entity -> boardToDto(entity));
        return new PageResultDto<>(result, function);
    }

    public PageResultDto<MemberList, Member> getMemberList(PageRequestDto requestDto, String tr_if) {
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());
        BooleanBuilder booleanBuilder = getMemberSearch(requestDto, tr_if);
        Page<Member> result = memberRepository.findAll(booleanBuilder, pageable);
        Function<Member, MemberList> function = (entity -> memberToDto(entity));
        return new PageResultDto<>(result, function);
    }

    private BooleanBuilder getMemberSearch(PageRequestDto requestDto, String tr_if) {
        String type = requestDto.getType();
        log.info("type = " + type);
        BooleanBuilder builder = new BooleanBuilder();
        QMember qMember = QMember.member;
        String keyword = requestDto.getKeyword();
        BooleanExpression gt = qMember.id.gt(0L);
        builder.and(qMember.user_role.contains(tr_if));
        builder.andNot(qMember.user_id.contains("admin"));
        builder.and(gt);

        if (type == null || type.trim().length() == 0) {
            return builder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
//        conditionBuilder.and(qMember.user_role.contains(tr_if));
//        conditionBuilder.andNot(qMember.user_id.contains("admin"));
        if (type.contains("u")) {
            conditionBuilder.or(qMember.user_id.contains(keyword));
        }
        if (type.contains("n")) {
            conditionBuilder.or(qMember.user_name.contains(keyword));
        }
        builder.and(conditionBuilder);

        log.info("value = " + builder.getValue());
        return builder;
    }

    private BooleanBuilder getSearch(PageRequestDto requestDto) {
        String type = requestDto.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBoard qBoard = QBoard.board;
        String keyword = requestDto.getKeyword();
        BooleanExpression booleanExpression = qBoard.id.gt(0L);
        booleanBuilder.and(booleanExpression);

        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qBoard.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qBoard.content.contains(keyword));
        }
        if (type.contains("u")) {
            conditionBuilder.or(qBoard.user_id.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

    private BooleanBuilder getPaySearch(PageRequestDto requestDto) {
        String type = requestDto.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBuyerPt qBuyerPt = QBuyerPt.buyerPt;
        String keyword = requestDto.getKeyword();
        BooleanExpression booleanExpression = qBuyerPt.id.gt(0L);
        booleanBuilder.and(booleanExpression);

        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("n")) {
            conditionBuilder.or(qBuyerPt.trainerInfo.member.user_name.contains(keyword));
        }
        if (type.contains("i")) {
            conditionBuilder.or(qBuyerPt.trainerInfo.member.user_id.contains(keyword));
        }
        if (type.contains("u")) {
            conditionBuilder.or(qBuyerPt.member.user_name.contains(keyword));
        }
        if (type.contains("m")) {
            conditionBuilder.or(qBuyerPt.member.user_id.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
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

    private OooDto entityToDto(OneOnOne entity) {
        OooDto dto = OooDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .answer(entity.getAnswer())
                .user_id(entity.getUser_id())
                .regDate(entity.getRegDate())
                .modifiedDate(entity.getModDate())
                .build();
        return dto;
    }

    private MemberList memberToDto(Member entity) {
        MemberList memberList = MemberList.builder()
                .id(entity.getId())
                .user_id(entity.getUser_id())
                .user_pw(entity.getUser_pw())
                .user_pn(entity.getUser_pn())
                .user_name(entity.getUser_name())
                .user_email(entity.getUser_email())
                .address_normal(entity.getAddress_normal())
                .address_detail(entity.getAddress_detail())
                .user_rrn(entity.getUser_rrn())
                .user_gender(entity.getUser_gender())
                .user_role(entity.getUser_role())
                .regDate(entity.getRegDate())
                .build();
        return memberList;
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

    private BuyerPTDto buyerPtToDto(BuyerPt entity) {
        BuyerPTDto buyerPTDto = BuyerPTDto.builder()
                .id(entity.getId())
                .member(entity.getMember())
                .trainerInfo(entity.getTrainerInfo())
                .pt_amount(entity.getPt_amount())
                .pay_method(entity.getPay_method())
                .regDate(entity.getRegDate())
                .build();

        return buyerPTDto;
    }
    @Transactional
    public int updateView(Long id) {
        log.info("조회수 증가 서비스");
        return noticeRepository.updateView(id);
    }


}