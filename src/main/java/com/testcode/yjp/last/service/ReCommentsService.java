package com.testcode.yjp.last.service;

import com.testcode.yjp.last.domain.Comment;
import com.testcode.yjp.last.domain.ReComment;
import com.testcode.yjp.last.domain.dto.CommentsListResponseDto;
import com.testcode.yjp.last.domain.dto.CommentsUpdateRequestDto;
import com.testcode.yjp.last.domain.dto.ReCommentsListResponseDto;
import com.testcode.yjp.last.repository.CommentsRepository;
import com.testcode.yjp.last.repository.ReCommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReCommentsService {

    private final ReCommentsRepository reCommentsRepository;

    @Transactional(readOnly = true)
    public List<ReCommentsListResponseDto> findAllDesc() {
        return reCommentsRepository.findAllDesc().stream()
                .map(ReCommentsListResponseDto::new)
                .collect(Collectors.toList());
    }





    public void delete(Long id) {
        reCommentsRepository.deleteById(id);
    }

//    @Transactional(readOnly = true)
//    public List<ReCommentsListResponseDto> findCount(Long hb_num) {
//        return reCommentsRepository.findAllCount(hb_num).stream()
//                .map(ReCommentsListResponseDto::new)
//                .collect(Collectors.toList());
//    }

//    @Transactional(readOnly = true)
//    public List<ReCommentsListResponseDto> findAllCount(Long id) {
//        return reCommentsRepository.findAllCount(id).stream()
//                .map(ReCommentsListResponseDto::new)
//                .collect(Collectors.toList());
//    }




//    public Comment findByComments(Long ParentNum) {
//        return commentsRepository.findBy(ParentNum);
//    }

//
//    public Long update(Long id, CommentsUpdateRequestDto commentsUpdateRequestDto) {
//        log.info("comments update post service");
//        Comment comments = commentsRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + id));
//        comments.update(commentsUpdateRequestDto.getComments());
//        System.out.println(commentsUpdateRequestDto.getComments());
//        commentsRepository.save(comments);
//        return id;
//    }

//    public void delete(Long id) {
//        ReComment reComment = reCommentsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다=id" + id));
//        reCommentsRepository.delete(reComment);
//    }
}
