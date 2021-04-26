package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Diet;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.DietDto;
import com.testcode.yjp.last.repository.DietRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.DietService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

//    @PostMapping("/save/{id}/{modDate}")
//    public Diet save(@PathVariable Long id, @RequestBody Diet diet) {
//        log.info("dietcontroller api post");
//        Optional<Member> result = memberRepository.findById(id);
//        diet.setMember(result.get());
//        dietRepository.save(diet);
//        return diet;
//    }

    // 삭제
    @PostMapping("/delete/{id}")
    public Long delete(@PathVariable Long id){
        dietService.delete(id);
        return id;
    }

//    @GetMapping("/list/{id}/{modDate}")
//    public ResponseEntity<List<DietDto>> list(@PathVariable("id") String id, @PathVariable("modDate") String modDate) {
//        log.info("dietapicontrolleraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//        log.info("잘넘어오나 보자 : " + id + " : " + modDate);
//        List<DietDto> modDateId = dietService.getDietListByModDate_Id(id, modDate);
//        log.info("잘 검색되나연 : " + modDateId);
//        return new ResponseEntity<>(modDateId, HttpStatus.OK);
//    }
}
