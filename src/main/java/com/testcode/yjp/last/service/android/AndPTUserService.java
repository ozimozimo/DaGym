package com.testcode.yjp.last.service.android;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.dto.android.AndMemberMypageDto;
import com.testcode.yjp.last.domain.dto.android.AndPTUserApplyMemberDto;
import com.testcode.yjp.last.domain.dto.android.AndPTUserSaveDto;
import com.testcode.yjp.last.domain.dto.android.AndPTUserSearchDto;
import com.testcode.yjp.last.repository.android.AndroidMemberRepository;
import com.testcode.yjp.last.repository.android.AndroidPTUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AndPTUserService {
    private final AndroidPTUserRepository androidPTUserRepository;
    private final AndroidMemberRepository androidMemberRepository;

    public ArrayList<AndPTUserSearchDto> getTrainers(String search) {
        // repository에서 select해줌. 
        // user_role like 'trainer' and (m.user_id like %:search% or m.user_name like %:search%
        // role이 트레이너면서 id, name중 검색어가 들어가는 트레이너 검색 
        ArrayList<Member> searchTrainer = androidPTUserRepository.findTrainer(search);
        
        // getDtos에서 ArrayList<Member> -> ArrayList<AndPTUserSearchDto>로 변환
        
        /*
        * 변환 방법 = 
        * ArrayList<Member>의 내용 = Member를
        * ArrayList<AndPTUserSearchDto>의 내용 AndPTUserSearchDto로
        * andPTUserSearchDto.set 으로 하나하나 값을 넣어준 뒤,
        * ArrayList<AndPTUserSearchDto>.add로 저장, 리턴
        * */
        return getDtos(searchTrainer);
    }

    public ArrayList<AndPTUserSearchDto> getTrainers(ArrayList<Member> trainerAll) {
        return getDtos(trainerAll);
    }

    public void extracted(Long member_id, Long trainer_id, AndPTUserSaveDto andPTUserSaveDto) {
        Member member = androidMemberRepository.findById(member_id).get();
        Member trainer = androidMemberRepository.findById(trainer_id).get();
        String start_date = andPTUserSaveDto.getStart_date();
        String end_date = andPTUserSaveDto.getEnd_date();

        // 이미 신청한 기록이 있는지 확인하기위해서 repository select해줌 (findPTUserBy)
        PTUser ptUserBy = androidPTUserRepository.findPTUserBy(member.getUser_id(), trainer);

        try {
            // 신청한 기록이 있고, condition이 0이거나 2인사람은 값을 update해줌.
            if (ptUserBy.getAccept_condition().equals("0")
                    || ptUserBy.getAccept_condition().equals("2")) {
                ptUserBy.setMember_id(member);
                ptUserBy.setTrainer_id(trainer);

                ptUserBy.setStart_date(start_date);
                ptUserBy.setEnd_date(end_date);
                ptUserBy.setUser_id(member.getUser_id());
                ptUserBy.setUser_name(member.getUser_name());
                ptUserBy.setUser_pn(member.getUser_pn());
                ptUserBy.setUser_email(member.getUser_email());
                ptUserBy.setAccept_condition("0"); // 신청옴. (보류)

                androidPTUserRepository.save(ptUserBy);
            }
            // 신청은 해뒀는데 condition 1이면 이미 트레이너와 연결되어 있으니 update와 save 둘다 안해줌.
            else if (ptUserBy.getAccept_condition().equals("1")) {
            }
        } catch (Exception e) {
            // 신청한 기록 없으면 저장해줌.
            PTUser ptUser = new PTUser();
            ptUser.setMember_id(member);
            ptUser.setTrainer_id(trainer);

            ptUser.setStart_date(start_date);
            ptUser.setEnd_date(end_date);
            ptUser.setUser_id(member.getUser_id());
            ptUser.setUser_name(member.getUser_name());
            ptUser.setUser_pn(member.getUser_pn());
            ptUser.setUser_email(member.getUser_email());
            ptUser.setAccept_condition("0"); // 신청옴. (보류)

            androidPTUserRepository.save(ptUser);
        }
    }

    public ArrayList<AndPTUserApplyMemberDto> getAndPTUserSearchDtos(Long member_id) {
        Member member = androidMemberRepository.findById(member_id).get();
        ArrayList<PTUser> ptUsers = androidPTUserRepository.requestList(member);
        ArrayList<AndPTUserApplyMemberDto> andPTUserApplyMemberDtos = new ArrayList<>();
        for (PTUser ptUser : ptUsers) {
            AndPTUserApplyMemberDto andPTUserApplyMemberDto = new AndPTUserApplyMemberDto(
                    ptUser.getId(),
                    ptUser.getUser_name(),
                    ptUser.getUser_id(),
                    ptUser.getStart_date(),
                    ptUser.getEnd_date()
            );
            andPTUserApplyMemberDtos.add(andPTUserApplyMemberDto);
        }
        return andPTUserApplyMemberDtos;
    }

    public AndMemberMypageDto getTrainers(Long member_id) {
        log.info("service getTrainers in");
        Member member = androidMemberRepository.findById(member_id).get();
        log.info("member name = " + member.getUser_name());
        // 트레이너 검색
        Member trainer = androidPTUserRepository.selectTrainer(member);

        AndMemberMypageDto andMemberMypageDto = null;
        try {
            // dto에 추가 후 리턴
            // trainer가 null이면 exception떠서 catch에서 처리
            andMemberMypageDto = new AndMemberMypageDto(
                    trainer.getId(),
                    trainer.getUser_id(),
                    trainer.getUser_pw(),
                    trainer.getUser_name(),
                    trainer.getUser_pn(),
                    trainer.getUser_email(),
                    trainer.getAddress_normal(),
                    trainer.getAddress_detail(),
                    trainer.getUser_rrn(),
                    trainer.getUser_pn(),
                    trainer.getUser_role()
            );
        } catch (Exception e) {
            // dto에 아무 값도 안넣어주고 리턴
            andMemberMypageDto = new AndMemberMypageDto(
                    null, null, null, null, null
            );
        }
        return andMemberMypageDto;
    }

    public ArrayList<AndPTUserSearchDto> getMembers(Long trainer_id) {
        Member member = androidMemberRepository.findById(trainer_id).get();

        ArrayList<PTUser> members = androidPTUserRepository.selectMember(member);
        ArrayList<AndPTUserSearchDto> andPTUserSearchDtos = new ArrayList<>();
        for (PTUser ptUser : members) {
            AndPTUserSearchDto andPTUserSearchDto = new AndPTUserSearchDto(
                    ptUser.getId(),
                    ptUser.getUser_name(),
                    ptUser.getUser_id(),
                    ptUser.getUser_email(),
                    ptUser.getUser_pn()
            );
            andPTUserSearchDtos.add(andPTUserSearchDto);
        }
        return andPTUserSearchDtos;
    }

    private ArrayList<AndPTUserSearchDto> getDtos(ArrayList<Member> searchTrainer) {
        ArrayList<AndPTUserSearchDto> andMemberMypageDtos = new ArrayList<>();

        for (Member member : searchTrainer) {
            AndPTUserSearchDto andPTUserSearchDto = new AndPTUserSearchDto(
                    member.getId(),
                    member.getUser_name(),
                    member.getUser_id(),
                    member.getUser_email(),
                    member.getUser_pn()
            );

            andMemberMypageDtos.add(andPTUserSearchDto);
        }
        return andMemberMypageDtos;
    }


}
