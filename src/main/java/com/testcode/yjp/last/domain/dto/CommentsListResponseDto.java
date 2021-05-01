package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CommentsListResponseDto {

    private Long id;
    private String user_id;
    private Long parentNum;
    private String comments;
    private int dislike;
    private int likes;
    private int reComments_count;
    private LocalDateTime modifiedDate;

    public CommentsListResponseDto(Comment entity){
        this.id = entity.getId();
        this.user_id = entity.getUser_id();
        this.parentNum = entity.getParentNum();
        this.comments = entity.getComments();
        this.likes = entity.getLike_check();
        this.dislike = entity.getDislike_check();
        this.reComments_count = entity.getReComments_count();
        this.modifiedDate = entity.getModDate();
    }
}
