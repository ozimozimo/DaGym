package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Diet;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.DietDto;
import com.testcode.yjp.last.repository.DietRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.DietService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/diet")
@Slf4j
public class DietApiController {

    private final DietService dietService;
    private final DietRepository dietRepository;
    private final MemberRepository memberRepository;

    // 오늘 데이터 보여주기
    @GetMapping("/dietToday")
    public List<DietDto> dietToday(String id, String diet_date) {
        log.info("Today Diet Data");
        System.out.println("id = " + id);
        System.out.println("diet_date = " + diet_date);
        List<DietDto> byIdWithDietDate = dietService.findByIdWithDietDate(id, diet_date);
        System.out.println("byIdWithDietDate = " + byIdWithDietDate);
        log.info("Today Data HOLY MOLY");
        return byIdWithDietDate;
    }

    // 클릭한 날짜 데이터 보여주기
    @GetMapping("/clickDate")
    public List<DietDto> clickDate(String id, String diet_date) {
        log.info("Clicked Diet Date Data");
        System.out.println("id = " + id);
        System.out.println("diet_date = " + diet_date);
        List<DietDto> byIdWithDietDate = dietService.findByIdWithDietDate(id, diet_date);
        System.out.println("dietService.findByIdWithDietDate(id, diet_date) = " + byIdWithDietDate);
        log.info("Clicked Diet Date Data HOLY MOLY");
        return byIdWithDietDate;
    }

    // 데이터 추가
    @PostMapping("/save/{id}")
    public Diet save(@PathVariable String id, @RequestBody Diet diet) {
        log.info("DietController Save Api Post");
        Member result = memberRepository.findId(id);
        Long member_id = result.getId();
        Optional<Member> byId = memberRepository.findById(member_id);

        diet.setMember(byId.get());
        dietRepository.save(diet);
        return diet;
    }

    // 데이터 삭제
    @PostMapping("/delete/{id}")
    public Long delete(@PathVariable Long id){
        log.info("DietController Delete Api Post");
        dietService.delete(id);
        return id;
    }
}
