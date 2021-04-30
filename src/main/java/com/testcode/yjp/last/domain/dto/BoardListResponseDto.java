package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BoardListResponseDto {
    private Long id;
    private String title;
    private String user_id;
    private String content;
    private int hit;
    private int recommends;
    private int likes;

    private LocalDateTime modifiedDate;

    public BoardListResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.user_id = entity.getUser_id();
        this.modifiedDate = entity.getModDate();
        this.recommends = entity.getRecommends().size();
        this.hit = entity.getHit();
    }

    public BoardListResponseDto(Long id, String title, String user_id, String content, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.user_id = user_id;
        this.content = content;
        this.modifiedDate = modifiedDate;
    }
}
