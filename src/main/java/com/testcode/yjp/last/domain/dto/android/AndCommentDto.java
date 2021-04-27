package com.testcode.yjp.last.domain.dto.android;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AndCommentDto {
    private Long id;
    private String user_id;
    private String comments;
    private int like_check=0;
    private int dislike_check=0;
    private LocalDateTime modDate;

    public AndCommentDto(Long id, String user_id, String comments, LocalDateTime modDate) {
        this.id = id;
        this.user_id = user_id;
        this.comments = comments;
        this.modDate = modDate;
    }
}
