package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Diet;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.BoardUpdateRequestDto;
import com.testcode.yjp.last.repository.DietRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.DietService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/diet", method = {RequestMethod.POST})
@Slf4j
public class DietApiController {

    private final DietService dietService;
    private final DietRepository dietRepository;
    private final MemberRepository memberRepository;

    // 등록
    @PostMapping("/save/{id}")
    public Diet save(@PathVariable Long id, @RequestBody Diet diet) {
        log.info("dietcontroller api post");
        Optional<Member> result = memberRepository.findById(id);
        diet.setMember(result.get());
        dietRepository.save(diet);
        return diet;
    }
    // 수정

    // 삭제
    @PostMapping("/delete/{id}")
    public Long delete(@PathVariable Long id){
        dietService.delete(id);
        return id;
    }
}
