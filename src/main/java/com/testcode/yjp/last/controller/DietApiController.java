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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    // 오늘 데이터 조회
    @GetMapping("/listToday")
    public List<DietDto> listToday(String id, String regDate) {
        log.info("Today Data");
        System.out.println("id = " + id);
        System.out.println("regDate = " + regDate);
        List<DietDto> byIdWithRegDate = dietService.findByIdWithRegDate(id, regDate);
        System.out.println("byIdWithRegDate = " + byIdWithRegDate);
        log.info("Today Data holy moly");
        return byIdWithRegDate;
    }

    // 날짜별 데이터 조회
    @GetMapping("/listDate")
    public List<DietDto> listDate(String id, String modDate) {
        log.info("ID and ModDate");
        System.out.println("id = " + id);
        System.out.println("modDate = " + modDate);
        List<DietDto> byIdWithModDate = dietService.findByIdWithModDate(id, modDate);
        System.out.println("dietService.findByIdWithModDate(id, modDate) = " + byIdWithModDate);
        log.info("holy moly");
        return byIdWithModDate;
    }

    // 등록
    @PostMapping("/save/{id}")
    public Diet save(@PathVariable Long id, @RequestBody Diet diet) {
        log.info("dietcontroller api post");
        Optional<Member> result = memberRepository.findById(id);
        diet.setMember(result.get());
        dietRepository.save(diet);
        return diet;
    }

    // 삭제
    @PostMapping("/delete/{id}")
    public Long delete(@PathVariable Long id){
        dietService.delete(id);
        return id;
    }
}
