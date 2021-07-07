package com.testcode.yjp.last.service;


import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.MemberOut;
import com.testcode.yjp.last.domain.dto.MemberFindIdDto;
import com.testcode.yjp.last.domain.dto.MemberJoinDto;
import com.testcode.yjp.last.domain.dto.MemberList;
import com.testcode.yjp.last.domain.dto.MemberSoDto;
import com.testcode.yjp.last.repository.MemberOutRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberOutRepository memberOutRepository;

    // 회원가입
    @Transactional
    public Long save(MemberJoinDto memberJoinDto) {
        return memberRepository.save(memberJoinDto.toEntity()).getId();
    }

    // 중복검사
    @Transactional
    public String IdChk(String user_id) {
        System.out.println(memberRepository.findByUser_id(user_id));
        if (memberRepository.findByUser_id(user_id) != null) {
            return "YES";
        } else {
            return "NO";
        }
    }

    // 휴대폰 인증 번호
    public void certifiedPhoneNumber(String phoneNumber, String cerNum) {
        String api_key = "NCSE1LBEKZBYMTO4";
        String api_secret = "XDQAAVISPS9CJTKDVHWFA88MWTOBDQOS";
        Message coolsms = new Message(api_key, api_secret);
        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "01039057545");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "휴대폰인증 테스트 메시지 : 인증번호는" + "[" + cerNum + "]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version
        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }

    // 회원 전체 조회
    public List<MemberList> getMemberList() {
        List<Member> memberEntities = memberRepository.findAll(); // Member List 전체값
        List<MemberList> memberLists = new ArrayList<>(); //Member DTO 이름, 아이디, 등등~~
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


    // 회원업데이트
    public Long update(Long member_id, MemberFindIdDto memberFindIdDto) {
        Member members = memberRepository.findById(member_id).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다. id=" + member_id));
        members.update(memberFindIdDto.getUser_pw(), memberFindIdDto.getUser_name(), memberFindIdDto.getUser_pn(), memberFindIdDto.getUser_email(), memberFindIdDto.getAddress_normal(), memberFindIdDto.getAddress_detail(),
                memberFindIdDto.getUser_rrn(), memberFindIdDto.getUser_gender(), memberFindIdDto.getUser_role());
        log.info("post mypage service ");
        System.out.println(members.getUser_pw());

        memberRepository.save(members);
        return member_id;
    }

    // 회원 findById
    public MemberFindIdDto findById(Long member_id) {
        Member member = memberRepository.findById(member_id).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다. id=" + member_id));
        log.info("get mypage");
        return new MemberFindIdDto(member);
    }

    // 회원 삭제
    public String delete(Long id, String user_pw, String out_comments) {
        log.info("service post delete");
        Member delete = memberRepository.findByMemberOut(id, user_pw);
        if (delete == null) {
            return "1";
        } else {
            MemberOut memberOut = MemberOut.builder()
                    .out_comments(out_comments)
                    .out_id(id)
                    .build();
            memberOutRepository.save(memberOut);
            memberRepository.deleteById(id);
            return "2";
        }
    }

    /*
     * -Email을 통해 해당 email로 가입된 정보가 있는지 확인하고,
     * 가입된 정보가 있다면 입력받은 name과 등록된 name이 일치한지 여부를 리턴하는 메소드
     * */
//    @Transactional
//    public boolean userEmailCheck(String user_name, String user_email,String user_id) {
//        List<Member> member = memberRepository.findCheckPw(user_name, user_email ,user_id);
//        log.info("get userEmailCheck Service");
//        System.out.println(member);
//        if (member.isEmpty()) {
//            return true;
//        } else {
//            return false;
//        }
//
//    }

    // 소셜 구글 저장
    public String googleSave(MemberSoDto memberSoDto) {
        log.info("****googleSave Service In****");

        return memberRepository.save(memberSoDto.googleEntity()).getUser_name();
    }

    // 소셜 카카오 저장
    public String kakaoSave(MemberSoDto memberSoDto) {
        log.info("***kakao service in***");
        return memberRepository.save(memberSoDto.kakaoEntity()).getUser_name();
    }

    // ajax 로그인 값 다를때
    public String findMember(String user_id, String user_pw) {
        List<Member> create = memberRepository.findByMemberIn(user_id, user_pw);
        Member member = memberRepository.findMember(user_id, user_pw);

        if (create.isEmpty()) {
            return "1";
        } else {
            if (member.getUser_id().equals("admin")) {
                return "3";
            } else {
                return "2";
            }
        }


    }

}