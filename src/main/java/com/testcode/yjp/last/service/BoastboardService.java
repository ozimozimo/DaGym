package com.testcode.yjp.last.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.testcode.yjp.last.domain.*;
import com.testcode.yjp.last.domain.dto.*;
import com.testcode.yjp.last.repository.BoastboardRepository;
import com.testcode.yjp.last.repository.EmotionRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import groovy.util.logging.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class BoastboardService {

    private final BoastboardRepository boastboardRepository;
    private final MemberRepository memberRepository;
    private final EmotionRepository emotionRepository;

    public List<BoardListResponseDto> findAllDesc() {
        return boastboardRepository.findAllDesc()
                .stream()
                .map(BoardListResponseDto :: new)
                .collect(Collectors.toList());
    }

    public PageResultDto<BoastboardDto, Boastboard> getList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDto);
        Page<Boastboard> result = boastboardRepository.findAll(booleanBuilder,pageable);
        Function<Boastboard, BoastboardDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(result, fn);
    }

    private BooleanBuilder getSearch(PageRequestDto requestDto) {
        String type = requestDto.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBoastboard qBoastboard = QBoastboard.boastboard;
        String keyword = requestDto.getKeyword();
        BooleanExpression expression = qBoastboard.id.gt(0L); // id > 0;
        booleanBuilder.and(expression); // select * from board where id > 0;

        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qBoastboard.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qBoastboard.content.contains(keyword));
        }
        if (type.contains("u")) {
            conditionBuilder.or(qBoastboard.user_id.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

    private BoastboardDto entityToDto(Boastboard boastboard) {
        BoastboardDto boastboardDto = BoastboardDto.builder()
                .id(boastboard.getId())
                .title(boastboard.getTitle())
                .content(boastboard.getContent())
                .user_id(boastboard.getUser_id())
                .hit(boastboard.getHit())
                .regDate(boastboard.getRegDate())
                .modifiedDate(boastboard.getModDate())
                .build();
        return boastboardDto;
    }

    public BoastboardResponseDto findById(Long id) {
        Boastboard entity = boastboardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다=id=" + id));
        return new BoastboardResponseDto(entity);
    }

    public Long update(Long id, BoardUpdateRequestDto boardUpdateRequestDto) {
        Boastboard boastboard = boastboardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        boastboard.update(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent(), boardUpdateRequestDto.getUuid(),
                boardUpdateRequestDto.getFileName(), boardUpdateRequestDto.getImgName());
        boastboardRepository.save(boastboard);
        return id;
    }

    public void delete(Long id) {
        boastboardRepository.deleteById(id);
    }

    public String saveEmo(Long member_id, Long bb_num, Emotion emotion) {
        Member member = memberRepository.findById(member_id).get();
        Boastboard boastboard = boastboardRepository.findById(bb_num).get();
        Emotion findAll = emotionRepository.findByAll(member, emotion.getEmotion(), boastboard);
        Emotion notEmo = emotionRepository.findNotEmo(member, emotion.getEmotion(), boastboard);
        if(findAll != null) {
            emotionRepository.delete(findAll);
            return null;
        } else if (notEmo != null) {
//            emotionRepository.delete(notEmo);
//            emotion.setMember(member);
//            emotion.setBoastboard(boastboard);
            notEmo.setEmotion(emotion.getEmotion());
            emotionRepository.save(notEmo);
            return notEmo.getEmotion();
        } else {
            emotion.setMember(member);
            emotion.setBoastboard(boastboard);
            emotionRepository.save(emotion);
            return emotion.getEmotion();
        }

    }


}

