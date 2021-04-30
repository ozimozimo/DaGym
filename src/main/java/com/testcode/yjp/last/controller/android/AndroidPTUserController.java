package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.android.AndMemberMypageDto;
import com.testcode.yjp.last.domain.dto.android.AndPTUserSaveDto;
import com.testcode.yjp.last.domain.dto.android.AndPTUserSearchDto;
import com.testcode.yjp.last.domain.dto.android.AndTrainerSearchDto;
import com.testcode.yjp.last.repository.android.AndroidMemberRepository;
import com.testcode.yjp.last.repository.android.AndroidPTUserRepository;
import com.testcode.yjp.last.service.android.AndPTUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/android/ptUser")
public class AndroidPTUserController {
    private final AndroidPTUserRepository androidPTUserRepository;
    private final AndroidMemberRepository androidMemberRepository;
    private final AndPTUserService andPTUserService;


    // 트레이너 검색
    @PostMapping("/search")
    public ArrayList<AndPTUserSearchDto> search(@RequestBody AndTrainerSearchDto trainerSearchDto) {
        String search = trainerSearchDto.getSearch();

        return andPTUserService.getTrainers(search);
    }

    // 트레이너 전체조회
    @GetMapping("/selectAll")
    public ArrayList<AndPTUserSearchDto> searchAll() {
        ArrayList<Member> trainerAll = androidPTUserRepository.findTrainerAll();

        return andPTUserService.getTrainers(trainerAll);
    }

    // 신청
    @PostMapping("/apply/{member_id}/{trainer_id}")
    public void applyTo(@PathVariable("member_id") Long member_id, @PathVariable("trainer_id") Long trainer_id, @RequestBody AndPTUserSaveDto andPTUserSaveDto) {
        andPTUserService.extracted(member_id, trainer_id, andPTUserSaveDto);
    }

    // 일반회원 -> 자기 트레이너 조회
    @GetMapping("/find/trainer/{member_id}")
    public AndMemberMypageDto selectTrainers(@PathVariable("member_id") Long member_id) {
        log.info("selectTrainers in + :" + member_id);
        return andPTUserService.getTrainers(member_id);
    }

    // 트레이너 -> 자기회원 조회
    @GetMapping("/find/member/{trainer_id}")
    public ArrayList<AndPTUserSearchDto> selectMembers(@PathVariable("trainer_id") Long trainer_id) {
        return andPTUserService.getMembers(trainer_id);
    }
}
