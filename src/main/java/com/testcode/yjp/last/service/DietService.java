package com.testcode.yjp.last.service;

import com.testcode.yjp.last.domain.Diet;
import com.testcode.yjp.last.domain.dto.DietAddDto;
import com.testcode.yjp.last.domain.dto.DietDto;
import com.testcode.yjp.last.repository.DietRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class DietService {

    private final DietRepository dietRepository;
    private final MemberRepository memberRepository;

    // 식단 추가
    @Transactional
    public Long save(DietAddDto dietAddDto) {
        return dietRepository.save(dietAddDto.toEntity()).getDiet_id();
    }

    // 식단 내용 보여주기
    @Transactional(readOnly = true)
    public List<DietAddDto> findAll(String id) {
        System.out.println("service id=" + id);
        log.info("service list get");
        return dietRepository.findAllDesc(id).stream()
                .map(DietAddDto::new)
                .collect(Collectors.toList());
    }

//    @Transactional(readOnly = true)
//    public List<DietAddDto> findByIdWithModDate(String id, LocalDateTime modDate) {
//        log.info("service id, modDate get");
//        System.out.println("id = " + id);
//        System.out.println("modDate = " + modDate);
//        return dietRepository.findByIdWithModDate(id, modDate).stream()
//                .map(DietAddDto::new)
//                .collect(Collectors.toList());
//    }

    @Transactional
    public List<DietDto> findByIdWithModDate(String id, String modDate) {
        log.info("service id, modDate get");
        System.out.println("id = " + id);
        System.out.println("modDate = " + modDate);
        return dietRepository.findByIdWithModDate(id, modDate).stream()
                .map(DietDto::new)
                .collect(Collectors.toList());
    }

//    public List<DietDto> getDietListByModDate_Id(String id) {
//
//        // 시간값 디비에 들어가있는 형식에 맞게
//        // id, modDate - String modDate
//        List<Diet> allDesc = dietRepository.findAllDesc(id);
//        List<DietDto> dietDtoList = new ArrayList<>();
//        allDesc.forEach(i -> {
//            DietDto dietDto = new DietDto(
//                    i.getDiet_id(),
//                    i.getDiet_name(),
//                    i.getDiet_kcal(),
//                    i.getDiet_carbo(),
//                    i.getDiet_protein(),
//                    i.getDiet_fat(),
//                    i.getDiet_time()
//            );
//            dietDtoList.add(dietDto);
//        });
//        return dietDtoList;
//    }

    @Transactional
    public void delete(Long id) {
//        Diet diet = dietRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no diet id" + id));
        dietRepository.deleteById(id);
    }
}
