package com.testcode.yjp.last.service;

import com.testcode.yjp.last.domain.dto.DietAddDto;
import com.testcode.yjp.last.domain.dto.DietDto;
import com.testcode.yjp.last.repository.DietRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class DietService {

    private final DietRepository dietRepository;

    // 이거 뭐더라
    @Transactional(readOnly = true)
    public List<DietAddDto> findAll(String id) {
        System.out.println("service id=" + id);
        log.info("service list get");
        return dietRepository.findAllDesc(id).stream()
                .map(DietAddDto::new)
                .collect(Collectors.toList());
    }

    // 클릭한 날짜 데이터 보여주기
    @Transactional
    public List<DietDto> findByIdWithDietDate(String id, String diet_date) {
        log.info("service id, diet_date get");
        System.out.println("id = " + id);
        System.out.println("diet_date = " + diet_date);
        return dietRepository.findByIdWithDietDate(id, diet_date).stream()
                .map(DietDto::new)
                .collect(Collectors.toList());
    }

    // 데이터 추가
    @Transactional
    public Long save(DietAddDto dietAddDto) {
        return dietRepository.save(dietAddDto.toEntity()).getDiet_id();
    }

    // 데이터 삭제
    @Transactional
    public void delete(Long id) {
        dietRepository.deleteById(id);
    }
}
