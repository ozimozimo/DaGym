package com.testcode.yjp.last.service.android;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.dto.android.AndMemberMypageDto;
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
        ArrayList<Member> searchTrainer = androidPTUserRepository.findTrainer(search);

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

    public AndMemberMypageDto getTrainers(Long member_id) {
        log.info("service getTrainers in");
        Member member = androidMemberRepository.findById(member_id).get();
        log.info("member name = " + member.getUser_name());

        Member trainer = androidPTUserRepository.selectTrainer(member);
        AndMemberMypageDto andMemberMypageDto = new AndMemberMypageDto(
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