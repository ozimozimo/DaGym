package com.testcode.yjp.last.service;

import com.testcode.yjp.last.repository.BoardImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardImageService {

    private final BoardImageRepository boardImageRepository;


}
