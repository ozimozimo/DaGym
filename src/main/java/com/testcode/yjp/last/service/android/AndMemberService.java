package com.testcode.yjp.last.service.android;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.MemberSoDto;
import com.testcode.yjp.last.domain.dto.android.AndMemberFindIdDto;
import com.testcode.yjp.last.domain.dto.android.AndMemberFindPwDto;
import com.testcode.yjp.last.domain.dto.android.AndMemberLoginDto;
import com.testcode.yjp.last.domain.dto.android.AndMemberMypageDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.android.AndroidMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AndMemberService {
    private final MemberRepository memberRepository;
    private final AndroidMemberRepository androidMemberRepository;
    public Member signIn(AndMemberLoginDto andMemberLoginDto) {
        String user_id = andMemberLoginDto.getUser_id();
        String user_pw = andMemberLoginDto.getUser_pw();
        Member member = memberRepository.findMember(user_id, user_pw);

        return member;
    }

    public Member findId(AndMemberFindIdDto andMemberFindIdDto) {
        String user_name = andMemberFindIdDto.getUser_name();
        String user_pn = andMemberFindIdDto.getUser_pn();
        Member member = androidMemberRepository.findId(user_name, user_pn);

        return member;
    }

    public Member findPw(AndMemberFindPwDto andMemberFindPwDto) {
        String user_name = andMemberFindPwDto.getUser_name();
        String user_pn = andMemberFindPwDto.getUser_pn();
        String user_id = andMemberFindPwDto.getUser_id();

        Member member = androidMemberRepository.findPw(user_name, user_pn, user_id);

        return member;
    }

    public Member updatePw(Long id, String user_pw) {
        user_pw = user_pw.replaceAll("\\\"", "");
        Member member = memberRepository.findById(id).orElse(null);

        member.setUser_pw(user_pw);

        return memberRepository.save(member);
    }

    public Member socialInsert(MemberSoDto memberSoDto) {
        String user_id = memberSoDto.getUser_id();
        Member byUser_id = memberRepository.findByUser_id(user_id);
        if (byUser_id == null) {
            Member member = memberRepository.save(memberSoDto.googleEntity());
            return member;
        } else {
            return byUser_id;
        }
    }

    public Member mypageUpdate(Long id, AndMemberMypageDto andMemberMypageDto) {
        Member byId = memberRepository.findById(id).orElse(null);
        byId.update(andMemberMypageDto.getUser_pw(), andMemberMypageDto.getUser_name(), andMemberMypageDto.getUser_email(), andMemberMypageDto.getAddress_normal(), andMemberMypageDto.getAddress_detail(), andMemberMypageDto.getUser_role());
        Member save = memberRepository.save(byId);

        return save;
    }

    @Transactional
    public String IdChk(String user_id) {
        System.out.println(memberRepository.findByUser_id(user_id));
        if (memberRepository.findByUser_id(user_id) != null) {
            return "YES";
        } else {
            return "NO";
        }
    }

}
