package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.InBody;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.android.AndInBodyDto;
import com.testcode.yjp.last.repository.android.AndroidInBodyRepository;
import com.testcode.yjp.last.repository.android.AndroidMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@Slf4j
@Transactional
@RequestMapping("/android/inbody")
public class AndroidInBodyController {
    private final AndroidInBodyRepository androidInBodyRepository;
    private final AndroidMemberRepository androidMemberRepository;

    @PostMapping("select")
    public ArrayList<AndInBodyDto> selectInbody(@RequestBody AndInBodyDto andInBodyDto) {
        String inBody_user_id = andInBodyDto.getInBody_user_id();
        ArrayList<InBody> byUserId = androidInBodyRepository.findByUserId(inBody_user_id);
        ArrayList<AndInBodyDto> andInBodyDtos = new ArrayList<>();
        for (InBody inBody : byUserId) {
            AndInBodyDto dto = new AndInBodyDto();
            dto.setId(inBody.getId());
            dto.setInBody_user_id(inBody.getInBody_user_id());
            dto.setInBody_weight(inBody.getInBody_weight());
            dto.setInBody_rmr(inBody.getInBody_rmr());
            dto.setInBody_bfp(inBody.getInBody_bfp());
            dto.setInBody_smm(inBody.getInBody_smm());
            dto.setInBody_date(inBody.getInBody_date());
            andInBodyDtos.add(dto);
        }
        return andInBodyDtos;
    }

    @PostMapping("save/{member_id}")
    public void saveInBody(@RequestBody InBody inBody, @PathVariable("member_id") Long member_id) {
        Member member = androidMemberRepository.findById(member_id).get();
        String inBody_date = inBody.getInBody_date();

        InBody byInBody = androidInBodyRepository.findByInBody(member.getUser_id(), inBody_date);
        if (byInBody != null) {
            byInBody.setInBody_weight(inBody.getInBody_weight());
            byInBody.setInBody_smm(inBody.getInBody_smm());
            byInBody.setInBody_bfp(inBody.getInBody_bfp());
            byInBody.setInBody_rmr(inBody.getInBody_rmr());
            androidInBodyRepository.save(byInBody);
        } else {
            inBody.setMember(member);
            inBody.setInBody_user_id(member.getUser_id());
            androidInBodyRepository.save(inBody);
        }
    }

    @PutMapping("update")
    public void updateInbody(@RequestBody InBody i) {
        Long id = i.getId();
        InBody findByInBody = androidInBodyRepository.findById(id).get();
        findByInBody.setInBody_rmr(i.getInBody_rmr());
        findByInBody.setInBody_smm(i.getInBody_smm());
        findByInBody.setInBody_bfp(i.getInBody_bfp());
        findByInBody.setInBody_weight(findByInBody.getInBody_weight());
        findByInBody.setInBody_date(i.getInBody_date());

        androidInBodyRepository.save(findByInBody);
    }

    @DeleteMapping("delete/{inbody_id}")
    public ArrayList<AndInBodyDto> deleteInbody(@PathVariable("inbody_id") Long inbody_id) {
        InBody findInbody = androidInBodyRepository.findById(inbody_id).get();
        String inBody_user_id = findInbody.getInBody_user_id();
        androidInBodyRepository.delete(findInbody);
        ArrayList<InBody> byUserId = androidInBodyRepository.findByUserId(inBody_user_id);
        ArrayList<AndInBodyDto> andInBodyDtos = new ArrayList<>();
        for (InBody inBody : byUserId) {
            AndInBodyDto dto = new AndInBodyDto();
            dto.setId(inBody.getId());
            dto.setInBody_user_id(inBody.getInBody_user_id());
            dto.setInBody_weight(inBody.getInBody_weight());
            dto.setInBody_rmr(inBody.getInBody_rmr());
            dto.setInBody_bfp(inBody.getInBody_bfp());
            dto.setInBody_smm(inBody.getInBody_smm());
            dto.setInBody_date(inBody.getInBody_date());
            andInBodyDtos.add(dto);
        }
        return andInBodyDtos;
    }
}
