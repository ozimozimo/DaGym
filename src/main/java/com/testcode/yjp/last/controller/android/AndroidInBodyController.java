package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.InBody;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.android.AndInBodyDto;
import com.testcode.yjp.last.repository.android.AndroidInBodyRepository;
import com.testcode.yjp.last.repository.android.AndroidMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@Slf4j
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
        inBody.setMember(member);
        inBody.setInBody_user_id(member.getUser_id());

        androidInBodyRepository.save(inBody);
    }
}
