package com.testcode.yjp.last.service;

import com.testcode.yjp.last.domain.dto.AdviceDto;
import com.testcode.yjp.last.repository.AdviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdviceService {

    private final AdviceRepository adviceRepository;

    @Transactional(readOnly = true)
    public List<AdviceDto> findAllDesc() {
        return adviceRepository.findAllDesc().stream()
                .map(AdviceDto::new)
                .collect(Collectors.toList());
    }

}
