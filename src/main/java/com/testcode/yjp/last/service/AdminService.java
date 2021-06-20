package com.testcode.yjp.last.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.Notice;
import com.testcode.yjp.last.domain.QBoard;
import com.testcode.yjp.last.domain.dto.*;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;

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

}