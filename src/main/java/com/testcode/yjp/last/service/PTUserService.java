package com.testcode.yjp.last.service;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.dto.MemberList;
import com.testcode.yjp.last.domain.dto.PTUserApply;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.PTUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PTUserService {

    private final PTUserRepository ptUserRepository;
    private final MemberRepository memberRepository;

    //이름검색
    public List<MemberList> nameSearch(String search) {
        log.info("service = " + search);
        List<Member> trainer = ptUserRepository.findTrainerN(search);
        List<MemberList> memberLists = new ArrayList<>();

        for (Member member : trainer) {
            MemberList memberList = getMemberList(member);
            memberLists.add(memberList);
        }
        log.info("memberLists = " + memberLists.toString());

        return memberLists;
    }

    //id 검색
    public List<MemberList> idSearch(String search) {
        log.info("service = " + search);
        List<Member> trainer = ptUserRepository.findTrainerI(search);
        List<MemberList> memberLists = new ArrayList<>();

        for (Member member : trainer) {
            MemberList memberList = getMemberList(member);
            memberLists.add(memberList);
        }
        log.info("memberLists = " + memberLists.toString());

        return memberLists;
    }

    //헬스장 검색
    public List<MemberList> addrSearch(String search) {
        log.info("service = " + search);
        List<Member> trainer = ptUserRepository.findTrainerA(search);
        List<MemberList> memberLists = new ArrayList<>();

        for (Member member : trainer) {
            MemberList memberList = getMemberList(member);
            memberLists.add(memberList);
        }
        log.info("memberLists = " + memberLists.toString());

        return memberLists;
    }


    public List<MemberList> getMemberList(Long member_id) {
        List<Member> memberEntities = ptUserRepository.findMemberId(member_id);
        List<MemberList> memberLists = new ArrayList<>();
        for (Member member : memberEntities) {
            MemberList memberList = MemberList
                    .builder()
                    .user_id(member.getUser_id())
                    .user_pw(member.getUser_pw())
                    .user_name(member.getUser_name())
                    .user_pn(member.getUser_pn())
                    .user_email(member.getUser_email())
                    .user_rrn(member.getUser_rrn())
                    .user_gender(member.getUser_gender())
                    .address_normal(member.getAddress_normal())
                    .address_detail(member.getAddress_detail())
                    .user_role(member.getUser_role())
                    .build();
            memberLists.add(memberList);
        }
        return memberLists;
    }

    private MemberList getMemberList(Member member) {
        return MemberList
                .builder()
                .id(member.getId())
                .user_name(member.getUser_name())
                .user_id(member.getUser_id())
                .user_pn(member.getUser_pn())
                .address_normal(member.getAddress_normal())
                .address_detail(member.getAddress_detail())
                .build();
    }

    // 신청내용 조회하기
    public ArrayList<PTUserApply> getPTUserApply(Long trainer_id) {
        Member trainer = memberRepository.findById(trainer_id).get();
        // member에서 trainer_id들만 찾아와서 trainer에 넣는다
        log.info("trainer = " + trainer);
        ArrayList<PTUser> ptUsers = ptUserRepository.requsetList(trainer);
        // PTUser에서 위에서 넣은 trainer만 찾아서 ptUsers에 넣는다
        ArrayList<PTUserApply> ptUserApplies = new ArrayList<>();
        // PTUserApply를 새로운 ArrayList로 선언하고
        for(PTUser ptUser : ptUsers) {
            // for each문 써서 ptUsers에 있는 값들을 ptUser에 넣어준다는데 이게 뭔소리지 시발
            // ptUser에서 가져온 것들을 PTUserApply에 맞춰서 넣어준다
            PTUserApply ptUserApply = new PTUserApply(
                    ptUser.getId(),
                    ptUser.getUser_name(),
                    ptUser.getMember_id().getId(),
                    // member_id는 Member타입인데 PTUserApply에서 Long 타입으로 선언해서 getId씀
                    ptUser.getStart_date(),
                    ptUser.getEnd_date(),
                    ptUser.getAccept_condition(),
                    ptUser.getTrainer_id().getId()
                    // trainer_id는 Member타입인데 PTUserApply에서 Long 타입으로 선언해서 getId씀
            );
            ptUserApplies.add(ptUserApply);
            log.info("ptUserApplies = " + ptUserApplies);
        }
        return ptUserApplies;
    }
}