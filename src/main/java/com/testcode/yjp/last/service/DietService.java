package com.testcode.yjp.last.service;

import com.testcode.yjp.last.domain.Diet;
import com.testcode.yjp.last.domain.dto.DietAddDto;
import com.testcode.yjp.last.repository.DietRepository;
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

    // 식단 추가
    @Transactional
    public Long save(DietAddDto dietAddDto) {
        return dietRepository.save(dietAddDto.toEntity()).getDiet_id();
    }

    // 식단 내용 보여주기
    @Transactional(readOnly = true)
    public List<DietAddDto> findAll(String id) {
        System.out.println("service id="+id);
        log.info("service list get");
        return dietRepository.findAllDesc(id).stream()
                .map(DietAddDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
//        Diet diet = dietRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no diet id" + id));
        dietRepository.deleteById(id);
    }
}
