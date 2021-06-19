package com.testcode.yjp.last.service;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.MemberList;
import com.testcode.yjp.last.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final MemberRepository memberRepository;

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
}