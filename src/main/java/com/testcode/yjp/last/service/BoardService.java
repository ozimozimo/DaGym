package com.testcode.yjp.last.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Comment;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.QBoard;
import com.testcode.yjp.last.domain.dto.*;
import com.testcode.yjp.last.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    // Board save 연관관계 매핑 처리부분
    public Long save(Long member_id,BoardSaveRequestDto boardSaveRequestDto){
        Optional<Member> memberId = memberRepository.findById(member_id);
        log.info("save post service");
        System.out.println(member_id);
        System.out.println(memberId);
        return boardRepository.save(boardSaveRequestDto.toEntity()).getId();
    }

    // 게시판 전체조회
    @Transactional(readOnly = true)
    public List<BoardListResponseDto> findAllDesc(){
        return boardRepository.findAllDesc().stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
    }

    // 게시판 findById
    public BoardResponseDto findById(Long id) {
        Board entity = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다=id=" + id));
        return new BoardResponseDto(entity);
    }

    // 게시판 수정
    public Long update(Long id, BoardUpdateRequestDto boardUpdateRequestDto) {
        log.info("update post controller");
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        board.update(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent());
        System.out.println(boardUpdateRequestDto.getTitle());
        System.out.println(boardUpdateRequestDto.getContent());
        boardRepository.save(board);
        return id;
    }

    // 게시판 삭제
    @Transactional
    public void delete(Long id) {


//        reCommentsRepository.deleteByreId(id);
//        commentsRepository.deleteByCmId(id);
        boardRepository.deleteById(id);
    }


    @Transactional
    public int updateView(Long id) {
        log.info("조회수 증가 서비스");
        return boardRepository.updateView(id);
    }



    public PageResultDto<BoardDto, Board> getList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDto);
        Page<Board> result = boardRepository.findAll(booleanBuilder,pageable);
        Function<Board, BoardDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(result, fn);
    }


    private BoardDto entityToDto(Board entity) {
        BoardDto dto = BoardDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .user_id(entity.getUser_id())
                .hit(entity.getHit())
                .recommend(entity.getRecommends().size())
                .regDate(entity.getRegDate())
                .modifiedDate(entity.getModDate())
                .build();
        return dto;
    }

    private BooleanBuilder getSearch(PageRequestDto requestDto) {  // Querydsl처리
        String type = requestDto.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBoard qBoard = QBoard.board;
        String keyword = requestDto.getKeyword();
        BooleanExpression expression = qBoard.id.gt(0L);
        booleanBuilder.and(expression);

        //검색조건이 없는경우
        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        // 검색조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qBoard.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qBoard.content.contains(keyword));
        }
        if (type.contains("u")) {
            conditionBuilder.or(qBoard.user_id.contains(keyword));
        }

        // 모든조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }



}