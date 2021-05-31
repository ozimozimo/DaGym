package com.testcode.yjp.last.service;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.dto.MemberList;
import com.testcode.yjp.last.domain.dto.PTUserApplyConDto;
import com.testcode.yjp.last.domain.dto.PTUserApplyMemberDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.PTUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    // 신청 전에 확인하기
    public ArrayList<PTUserApplyMemberDto> getCheckList(Long member_id) {
        // 트레이너 아이디 조회하면 신청했는 회원 아이디도 딸려온다
        Member member = memberRepository.findById(member_id).get();
        log.info("member_id = " + member);
        ArrayList<PTUser> ptUsers = ptUserRepository.checkApply(member);
        ArrayList<PTUserApplyMemberDto> ptUserApplyCheckDtos = new ArrayList<>();
        for(PTUser ptUser : ptUsers) {
            PTUserApplyMemberDto ptUserApplyCheckDto = new PTUserApplyMemberDto(
                    ptUser.getId(),
                    ptUser.getMember_id().getId(),
                    ptUser.getUser_id(),
                    ptUser.getUser_name(),
                    ptUser.getStart_date(),
                    ptUser.getEnd_date(),
                    ptUser.getAccept_condition(),
                    ptUser.getTrainer_id().getId(),
                    ptUser.getUser_pn()
            );
            ptUserApplyCheckDtos.add(ptUserApplyCheckDto);
            log.info("ptUserApplyCheckDtos = " + ptUserApplyCheckDtos);
        }
        return ptUserApplyCheckDtos;
    }

    // 수락회원 조회하기
    public ArrayList<PTUserApplyMemberDto> getAcceptList(Long trainer_id) {
        Member trainer = memberRepository.findById(trainer_id).get();
        log.info("trainer_id = " + trainer);
        ArrayList<PTUser> ptUsers = ptUserRepository.findAccept(trainer);
        ArrayList<PTUserApplyMemberDto> ptUserAccepts = new ArrayList<>();
        for(PTUser ptUser : ptUsers) {
            PTUserApplyMemberDto ptUserApplyMemberDto = new PTUserApplyMemberDto(
                    ptUser.getId(),
                    ptUser.getMember_id().getId(),
                    ptUser.getUser_id(),
                    ptUser.getUser_name(),
                    ptUser.getStart_date(),
                    ptUser.getEnd_date(),
                    ptUser.getAccept_condition(),
                    ptUser.getTrainer_id().getId(),
                    ptUser.getUser_pn()
            );
            ptUserAccepts.add(ptUserApplyMemberDto);
            log.info("ptUserAccepts : " + ptUserAccepts);
        }
        return ptUserAccepts;
    }

    // 신청내용 조회하기
    public ArrayList<PTUserApplyMemberDto> getPTUserApply(Long trainer_id) {
        Member trainer = memberRepository.findById(trainer_id).get();
        // member에서 trainer_id들만 찾아와서 trainer에 넣는다
        ArrayList<PTUser> ptUsers = ptUserRepository.findApply(trainer);
        // PTUser에서 위에서 넣은 trainer만 찾아서 ptUsers에 넣는다
        ArrayList<PTUserApplyMemberDto> ptUserApplies = new ArrayList<>();
        // PTUserApply를 새로운 ArrayList로 선언하고
        for (PTUser ptUser : ptUsers) {
            // for each문 써서 ptUsers에 있는 값들을 ptUser에 넣어준다는데 이게 뭔소리지 시발
            // ptUser에서 가져온 것들을 PTUserApply에 맞춰서 넣어준다
            PTUserApplyMemberDto ptUserApplyMemberDto = new PTUserApplyMemberDto(
                    ptUser.getId(),
                    ptUser.getMember_id().getId(),
                    ptUser.getUser_name(),
                    // member_id는 Member타입인데 PTUserApply에서 Long 타입으로 선언해서 getId씀
                    ptUser.getUser_id(),
                    ptUser.getStart_date(),
                    ptUser.getEnd_date(),
                    ptUser.getAccept_condition(),
                    ptUser.getTrainer_id().getId(),
                    ptUser.getUser_pn()
                    // trainer_id는 Member타입인데 PTUserApply에서 Long 타입으로 선언해서 getId씀
            );
            ptUserApplies.add(ptUserApplyMemberDto);
            log.info("ptUserApplies = " + ptUserApplies);
        }
        return ptUserApplies;
    }

    // 신청온거 표시
    public ArrayList<PTUserApplyMemberDto> getApplyCount(Long trainer_id) {
        Member trainer = memberRepository.findById(trainer_id).get();
        // member에서 trainer_id들만 찾아와서 trainer에 넣는다
        ArrayList<PTUser> ptUsers = ptUserRepository.countApply(trainer);
        // PTUser에서 위에서 넣은 trainer만 찾아서 ptUsers에 넣는다
        ArrayList<PTUserApplyMemberDto> ptUserCount = new ArrayList<>();
        // PTUserApply를 새로운 ArrayList로 선언하고
        for (PTUser ptUser : ptUsers) {
            // for each문 써서 ptUsers에 있는 값들을 ptUser에 넣어준다는데 이게 뭔소리지 시발
            // ptUser에서 가져온 것들을 PTUserApply에 맞춰서 넣어준다
            PTUserApplyMemberDto ptUserApplyMemberDto = new PTUserApplyMemberDto(
                    ptUser.getId(),
                    ptUser.getAccept_condition(),
                    ptUser.getTrainer_id().getId()
            );
            ptUserCount.add(ptUserApplyMemberDto);
            log.info("ptUserApplies = " + ptUserCount);
        }
        return ptUserCount;
    }


    // trainer.js에 updateAccpet()에서 받아온 값이 PTUserApplyConDto에 들어가있다
    // 거기서 받아온 id로 신청한 id를 찾아낸다
    // 그 id에 해당하는 accept_condition을 받아온 apply_if에 맞게 바꿔준다
    public Long update(Long id, PTUserApplyConDto ptUserApplyConDto) {
        log.info("ptuser update post service");
        PTUser ptUser = ptUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        ptUser.update(ptUserApplyConDto.getId(), ptUserApplyConDto.getApply_if());
        System.out.println("받아온 신청 ID: " + ptUserApplyConDto.getId());
        System.out.println("신청상태 : " + ptUserApplyConDto.getApply_if());
        ptUserRepository.save(ptUser);
        return id;
    }

}